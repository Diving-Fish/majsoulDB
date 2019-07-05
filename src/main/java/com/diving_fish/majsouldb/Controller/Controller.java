package com.diving_fish.majsouldb.Controller;

import com.diving_fish.majsouldb.Entity.Match;
import com.diving_fish.majsouldb.Entity.Player;
import com.diving_fish.majsouldb.Entity.Team;
import com.diving_fish.majsouldb.Repository.MatchRepo;
import com.diving_fish.majsouldb.Repository.PlayerRepo;
import com.diving_fish.majsouldb.Repository.TeamRepo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class Controller {
    @Autowired
    private TeamRepo teamRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private MatchRepo matchRepo;

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
        player.setTeam_id(id);
        playerRepo.save(player);
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
}
