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

    @GetMapping(value = "/getready")
    @ResponseBody
    public JSONArray getReady(@RequestParam("team_id") Long id, @RequestParam("round") Long round) {
        JSONArray array = new JSONArray();
        Ready ready = readyRepo.findByTeamIdAndRound(id, round);
        if (ready == null) return array;
        array.addAll(Arrays.asList(ready.get()));
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
        JSONArray players = body.getJSONArray("players");
        JSONArray teams = body.getJSONArray("teams");
        JSONArray scores = body.getJSONArray("scores");
        Match match = new Match(
                body.getInt("round"),
                body.getInt("position"),
                players.getLong(0),
                teams.getLong(0),
                scores.getLong(0),
                players.getLong(1),
                teams.getLong(1),
                scores.getLong(1),
                players.getLong(2),
                teams.getLong(2),
                scores.getLong(2),
                players.getLong(3),
                teams.getLong(3),
                scores.getLong(3)
        );
        matchRepo.save(match);
    }

    @PostMapping(value = "/settinggroup")
    @ResponseBody
    public void settingGroup(@RequestBody JSONArray body) {
        ArrayList<Long> arrayList = new ArrayList<>();
        for (Object o : body) {
           arrayList.add((Long) o);
        }
        Collections.shuffle(arrayList);
        for (int i = 0; i < body.size(); i += 4) {
            groupRepo.save(new Group(1, arrayList.get(i+1), arrayList.get(i+2), arrayList.get(i+3), arrayList.get(i)));
        }
    }
}
