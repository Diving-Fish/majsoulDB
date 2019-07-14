package com.diving_fish.majsouldb.Controller;

import com.diving_fish.majsouldb.Entity.*;
import com.diving_fish.majsouldb.Repository.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class Controller {
    @Autowired
    private TeamRepo teamRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private MatchRepo matchRepo;

    @Autowired
    private ReadyRepo readyRepo;

    @Autowired
    private GroupRepo groupRepo;

    @PostMapping(value = "/add_team")
    @ResponseBody
    public void addTeam(@RequestParam("name") String name) {
        Team team = new Team();
        team.setName(name);
        teamRepo.save(team);
    }

    @PostMapping(value = "/add_player")
    @ResponseBody
    public void addPlayer(@RequestParam("name") String name, @RequestParam("team_id") Long id) {
        Player player = new Player();
        player.setName(name);
        player.setTeamId(id);
        playerRepo.save(player);
    }

    @GetMapping(value = "/getallplayers")
    public JSONArray getAllPlayers() {
        JSONArray jsonArray1 = new JSONArray();
        JSONArray jsonArray = getAllTeams();
        for (Object object: jsonArray) {
            JSONObject object1 = (JSONObject) object;
            long team_id = object1.getLong("team_id");
            object1 = getPlayers(team_id);
            jsonArray1.add(object1);
        }
        return jsonArray1;
    }

    @GetMapping(value = "/getallteams")
    public JSONArray getAllTeams() {
        JSONArray jsonArray = new JSONArray();
        List<Team> teams = teamRepo.findAll();
        for (Team team : teams) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("team_id", team.getId());
            jsonObject.put("team_name", team.getName());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @GetMapping(value = "/getenabledteams")
    public JSONArray getEnabledTeams() {
        JSONArray jsonArray = new JSONArray();
        List<Team> teams = teamRepo.findAllByEnabled(true);
        for (Team team : teams) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("team_id", team.getId());
            jsonObject.put("team_name", team.getName());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @GetMapping(value = "/getplayers")
    @ResponseBody
    public JSONObject getPlayers(@RequestParam("team_id") Long id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("team_id", id);
        Team team = teamRepo.findTeamById(id);
        jsonObject.put("team_name", team.getName());
        List<Player> players = playerRepo.findPlayersByTeamId(id);
        JSONArray jsonArray = new JSONArray();
        for (Player player: players) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("id", player.getId());
            jsonObject1.put("name", player.getName());
            jsonArray.add(jsonObject1);
        }
        jsonObject.put("team", jsonArray);
        return jsonObject;
    }

    @GetMapping(value = "/team")
    @ResponseBody
    public String team(@RequestParam("team_id") Long id) {
        Team team = teamRepo.findTeamById(id);
        if (team == null) {
            return "未找到队伍：" + id;
        }
        else {
            String s = "队伍ID：" + id + "\n队伍名称：" + team.getName() + "\n队员：\n";
            List<Player> players = playerRepo.findPlayersByTeamId(id);
            for (Player player: players) {
                s += player.getName() + "（" + player.getId() + "）\n";
            }
            return s;
        }
    }

    @GetMapping(value = "/getready")
    @ResponseBody
    public JSONArray getReady(@RequestParam("team_id") Long id, @RequestParam("round") Long round) {
        JSONArray array = new JSONArray();
        Ready ready = readyRepo.findByTeamIdAndRound(id, round);
        if (ready == null) return array;
        array.addAll(Arrays.asList(ready.get()));
        return array;
    }

    @GetMapping(value = "/getallready")
    @ResponseBody
    public JSONArray getReady(@RequestParam("round") Long round) {
        JSONArray array = new JSONArray();
        for (Ready ready : readyRepo.findAllByRound(round)) {
            Long l[] = ready.get();
            JSONObject object = new JSONObject();
            object.put("team_id", l[0]);
            object.put("team_name", teamRepo.findTeamById(l[0]).getName());
            List<Long> longList = Arrays.asList(l).subList(2, 7);
            object.put("players", longList);
            List<String> nameList = new ArrayList<>();
            for (Long l2 : longList) {
                nameList.add(playerRepo.findPlayerById(l2).getName());
            }
            object.put("playernames", nameList);
            array.add(object);
        }
        return array;
    }

    @PostMapping(value = "/ready")
    @ResponseBody
    public boolean ready(@RequestBody JSONObject body) {
        Long round = body.getLong("round");
        Long team_id = body.getLong("team_id");
        Ready ready = readyRepo.findByTeamIdAndRound(team_id, round);
        if (ready == null) {
            ready = new Ready(team_id, round, body.getLong("id1"), body.getLong("id2"),body.getLong("id3"),body.getLong("id4"),body.getLong("id5"));
            readyRepo.save(ready);
            return true;
        } else {
            return false;
        }
    }

    @PostMapping(value = "/add_match")
    @ResponseBody
    public void addMatch(@RequestBody JSONObject body) {
        Long players = body.getLong("player");
        Long teams = body.getLong("team");
        Long scores = body.getLong("score");
        Match match = new Match(
                body.getInt("round"),
                body.getInt("position"),
                players,
                teams,
                scores
        );
        matchRepo.save(match);
    }

    @GetMapping(value = "/generate_auto_match")
    @ResponseBody
    public JSONArray generateAutoMatch(@RequestParam("round") int round) {
        JSONArray resp = new JSONArray();
        JSONArray groups = getGroup(round);
        for (Object group : groups) {
            JSONArray group_l = (JSONArray) group;
            JSONObject object = new JSONObject();
            JSONArray players = new JSONArray();
            JSONArray playerIds = new JSONArray();
            String tag = "";
            for (Object g : group_l) {
                Integer _g = (Integer) g;
                tag += _g.toString() + "_";
                JSONArray players2 = new JSONArray();
                JSONArray players3 = new JSONArray();
                Ready ready = readyRepo.findByTeamIdAndRound((long) _g, (long) round);
                if (ready != null) {
                    players2.addAll(Arrays.asList(ready.get()).subList(2, 7));
                    for (int i = 2; i < 7; i++) {
                        players3.add(playerRepo.findPlayerById(ready.get()[i]).getName());
                    }
                } else {
                    for (int i = 0; i < 5; i++) {
                        players2.add(-1);
                        players3.add("_COM_");
                    }
                }
                players.add(players3);
                playerIds.add(players2);
            }
            tag = tag.substring(0, tag.length() - 1);
            object.put("tag", tag);
            object.put("init_points", 100000);
            object.put("players", players);
            object.put("player_ids", playerIds);
            object.put("random_seat", true);
            object.put("spectating", true);
            object.put("broadcast", true);
            resp.add(object);
        }
        return resp;
    }

    @GetMapping(value = "/getgroup")
    @ResponseBody
    public JSONArray getGroup(@RequestParam Integer round) {
        JSONArray jsonArray = new JSONArray();
        for (Group group : groupRepo.findAllByRound(round)) {
            jsonArray.add(group.get());
        }
        return jsonArray;
    }

    @PostMapping(value = "/settinggroup")
    @ResponseBody
    public void settingGroup(@RequestBody List<Long> body, @RequestParam Integer round) {
        Collections.shuffle(body);
        for (int i = 0; i + 4 <= body.size(); i += 4) {
            groupRepo.save(new Group(round, body.get(i+1), body.get(i+2), body.get(i+3), body.get(i)));
        }
    }
}
