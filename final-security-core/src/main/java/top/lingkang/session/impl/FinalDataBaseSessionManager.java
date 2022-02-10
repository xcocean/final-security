package top.lingkang.session.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import top.lingkang.session.FinalSession;
import top.lingkang.session.SessionManager;
import top.lingkang.utils.SerializationUtils;

import java.sql.Blob;

/**
 * @author lingkang
 */
public class FinalDataBaseSessionManager implements SessionManager {

    private JdbcTemplate jdbcTemplate;

    public FinalDataBaseSessionManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFinalSession(String token, FinalSession finalSession) {
        try {
            Integer count = jdbcTemplate.queryForObject("select count(*) from fs_store where id=?", new Object[]{token}, Integer.class);

            if (count == 0) {
                jdbcTemplate.update(
                        "insert into fs_store(token,id,final_session) values(?,?,?)",
                        token, finalSession.getId(), SerializationUtils.serialization(finalSession)
                );
            } else {
                jdbcTemplate.update(
                        "update fs_store set final_session=?,id=? where token=?",
                        token, finalSession.getId(), SerializationUtils.serialization(finalSession));
            }
        } catch (Exception e) {
        }

    }

    @Override
    public FinalSession getSession(String token) {
        try {
            Blob blob = jdbcTemplate.queryForObject(
                    "select final_session from fs_store where token=?",
                    new Object[]{token},
                    Blob.class
            );
            return (FinalSession) SerializationUtils.unSerialization(blob.getBinaryStream());
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public FinalSession getSessionById(String id) {
        try {
            Blob blob = jdbcTemplate.queryForObject(
                    "select final_session from fs_store where id=?",
                    new Object[]{id},
                    Blob.class
            );
            return (FinalSession) SerializationUtils.unSerialization(blob.getBinaryStream());
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public String[] getRoles(String token) {

        return new String[0];
    }

    @Override
    public String[] getPermission(String token) {
        return new String[0];
    }

    @Override
    public void addRoles(String token, String... role) {

    }

    @Override
    public void deleteRoles(String token) {

    }

    @Override
    public void addPermission(String token, String... permission) {

    }

    @Override
    public void deletePermission(String token) {

    }

    @Override
    public FinalSession removeSession(String token) {
        return null;
    }

    @Override
    public boolean existsToken(String token) {
        return false;
    }

    @Override
    public boolean existsId(String id) {
        return false;
    }

    @Override
    public void updateLastAccessTime(String token) {

    }

    @Override
    public long getLastAccessTime(String token) {
        return 0;
    }

    @Override
    public void cleanExpires() {

    }
}
