package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Member;
import com.example.demo.model.Response;
import com.example.demo.utils.BCrypt;

@RestController
@RequestMapping("/brad07")
@CrossOrigin("*")
public class Brad07 {

    private static final Logger logger = LoggerFactory.getLogger(Brad07.class);

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Autowired
    private Response response;

    private final MemberRowMapper memberRowMapper = new MemberRowMapper();

    @PostMapping("/member")
    public Response insertMember(@RequestBody Member member) {
        logger.info("Inserting member: {}", member.getAccount());
        String checkSql = "SELECT COUNT(*) FROM member WHERE account = :account";
        Map<String, String> checkData = new HashMap<>();
        checkData.put("account", member.getAccount());
        Long count = template.queryForObject(checkSql, checkData, Long.class);
        if (count > 0) {
            response.setError(1);
            response.setMesg("Account already exists");
            response.setInsertId(0);
            response.setData(null);
            return response;
        }

        String sql = "INSERT INTO member (account, passwd, realname) VALUES (:account, :passwd, :realname)";
        Map<String, String> data = new HashMap<>();
        data.put("account", member.getAccount());
        data.put("passwd", BCrypt.hashpw(member.getPasswd(), BCrypt.gensalt()));
        data.put("realname", member.getRealname());

        try {
            int insertId = template.update(sql, data);
            response.setError(0);
            response.setMesg("OK");
            response.setInsertId(insertId);
            response.setData(null);
        } catch (Exception e) {
            response.setError(1);
            response.setMesg("Insert failed: " + e.getMessage());
            response.setInsertId(0);
            response.setData(null);
        }
        return response;
    }

    @DeleteMapping("/member/{id}")
    public Response deleteMember(@PathVariable Integer id) {
        logger.info("Deleting member with id: {}", id);
        String sql = "DELETE FROM member WHERE id = :id";
        Map<String, Integer> data = new HashMap<>();
        data.put("id", id);

        try {
            int n = template.update(sql, data);
            if (n > 0) {
                response.setError(0);
                response.setMesg("OK");
            } else {
                response.setError(1);
                response.setMesg("No member found with id: " + id);
            }
            response.setInsertId(0);
            response.setData(null);
        } catch (Exception e) {
            response.setError(1);
            response.setMesg("Delete failed: " + e.getMessage());
            response.setInsertId(0);
            response.setData(null);
        }
        return response;
    }

    @PutMapping("/member/{id}")
    public Response update(@PathVariable Integer id, @RequestBody Member member) {
        logger.info("Updating member with id: {}", id);
        String sql = "UPDATE member SET account = :account, realname = :realname WHERE id = :id";
        Map<String, Object> data = new HashMap<>();
        data.put("account", member.getAccount());
        data.put("realname", member.getRealname());
        data.put("id", id);

        try {
            int n = template.update(sql, data);
            if (n > 0) {
                response.setError(0);
                response.setMesg("OK");
            } else {
                response.setError(1);
                response.setMesg("No member found with id: " + id);
            }
            response.setInsertId(0);
            response.setData(null);
        } catch (Exception e) {
            response.setError(1);
            response.setMesg("Update failed: " + e.getMessage());
            response.setInsertId(0);
            response.setData(null);
        }
        return response;
    }

    @GetMapping("/members")
    public Response getAllMembers() {
        logger.info("Fetching all members");
        String sql = "SELECT id, account, passwd, realname FROM member";
        try {
            List<Member> members = template.query(sql, memberRowMapper);
            response.setError(0);
            response.setMesg("OK");
            response.setInsertId(0);
            response.setData(members);
        } catch (Exception e) {
            response.setError(1);
            response.setMesg("Query failed: " + e.getMessage());
            response.setInsertId(0);
            response.setData(null);
        }
        return response;
    }

    @GetMapping("/member/{id}")
    public Response getMemberById(@PathVariable Integer id) {
        logger.info("Fetching member with id: {}", id);
        String sql = "SELECT id, account, passwd, realname FROM member WHERE id = :id";
        Map<String, Integer> data = new HashMap<>();
        data.put("id", id);

        try {
            List<Member> members = template.query(sql, data, memberRowMapper);
            if (members.size() > 0) {
                response.setError(0);
                response.setMesg("OK");
                response.setInsertId(0);
                response.setData(members.get(0));
            } else {
                response.setError(1);
                response.setMesg("No member found with id: " + id);
                response.setInsertId(0);
                response.setData(new Member(-1L, null, null, null));
            }
        } catch (Exception e) {
            response.setError(1);
            response.setMesg("Query failed: " + e.getMessage());
            response.setInsertId(0);
            response.setData(null);
        }
        return response;
    }

    @GetMapping("/members/search/{keyword}")
    public Response searchMembers(@PathVariable String keyword) {
        logger.info("Searching members with keyword: {}", keyword);
        String sql = "SELECT id, account, passwd, realname FROM member WHERE account LIKE :keyword OR realname LIKE :keyword";
        Map<String, String> data = new HashMap<>();
        data.put("keyword", "%" + keyword + "%");

        try {
            List<Member> members = template.query(sql, data, memberRowMapper);
            response.setError(0);
            response.setMesg("OK");
            response.setInsertId(0);
            response.setData(members);
        } catch (Exception e) {
            response.setError(1);
            response.setMesg("Search failed: " + e.getMessage());
            response.setInsertId(0);
            response.setData(null);
        }
        return response;
    }

    private static class MemberRowMapper implements RowMapper<Member> {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Member(
                rs.getLong("id"),
                rs.getString("account"),
                rs.getString("passwd"),
                rs.getString("realname")
            );
        }
    }
} 