package spring2.zenoinstagram.src.user;

import spring2.zenoinstagram.config.BaseException;
import spring2.zenoinstagram.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import spring2.zenoinstagram.utils.SHA256;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers() {
        String getUsersQuery = "select userIdx,name,nickName,phone,email from Instagram.User";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("nickName"),
                        rs.getString("phone"),
                        rs.getString("email")
                ));
    }

    public GetUserRes getUsersByEmail(String email) {
        String getUsersByEmailQuery = "select userIdx,name,nickName,phone,email from Instagram.User where email=?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.queryForObject(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("nickName"),
                        rs.getString("phone"),
                        rs.getString("email")),
                getUsersByEmailParams);
    }


    public GetUserRes getUsersByIdx(int userIdx) {
        String getUsersByIdxQuery = "select userIdx,name,nickName,phone,email from Instagram.User where userIdx=?";
        int getUsersByIdxParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUsersByIdxQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("nickName"),
                        rs.getString("phone"),
                        rs.getString("email")),
                getUsersByIdxParams);
    }

    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "INSERT INTO Instagram.User (name, nickName, email, password, phone, profileImgUrl, website, introduce) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] createUserParams = new Object[]{postUserReq.getName(), postUserReq.getNickName(), postUserReq.getEmail(),
                postUserReq.getPassword(), postUserReq.getPhone(), postUserReq.getProfileImgUrl(), postUserReq.getWebsite(), postUserReq.getIntroduce()};

        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select email from Instagram.User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int checkNickName(String nickName) {
        String checkNickNameQuery = "select exists(select nickName from Instagram.User where nickName = ?)";
        String checkNickNameParams = nickName;
        return this.jdbcTemplate.queryForObject(checkNickNameQuery, int.class, checkNickNameParams);

    }

    public int checkPhone(String phone) {
        String checkPhoneQuery = "select exists(select phone from Instagram.User where phone = ?)";
        String checkPhoneParams = phone;
        return this.jdbcTemplate.queryForObject(checkPhoneQuery, int.class, checkPhoneParams);
    }

    public int checkUserPassword(String email, String password) {
        String checkPasswordQuery = "select password from Instagram.User where email = ?";
        String checkPasswordParams = email;
        String correctPassword = this.jdbcTemplate.queryForObject(checkPasswordQuery, String.class, checkPasswordParams);

        String encryptPassword = new SHA256().encrypt(password);
//        String encryptPassword = password; // jwt 비밀번호 미사용시

        if (correctPassword.equals(encryptPassword)) return 1;
        else return 0;
    }

    public int modifyUserName(PatchUserReq patchUserReq) {
        String modifyUserNameQuery = "update Instagram.User set nickName = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getNickName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams);
    }
    public String checkUserStatus(String email){
        String checkUserStatusQuery = "select status from Instagram.User where email=?";
        String checkUserEmailParams = email;

        return this.jdbcTemplate.queryForObject(checkUserStatusQuery, String.class, checkUserEmailParams);
    }
    public DelResUserRes deleteUser(DelResUserReq delResUserReq, String order) {
        String delResUserQuery = null;
        if (order.equals("delete"))
            delResUserQuery = "UPDATE Instagram.User SET status = 'INACTIVE' WHERE email=?";
        else if(order.equals("restore"))
            delResUserQuery = "UPDATE Instagram.User SET status = 'ACTIVE' WHERE email=?";
        String deleteUserEmailParams = delResUserReq.getEmail();

        this.jdbcTemplate.update(delResUserQuery, deleteUserEmailParams);

        String getUsersStatusQuery = "select name, nickName, phone, email, status from Instagram.User where email=?";
        return this.jdbcTemplate.queryForObject(getUsersStatusQuery,
                (rs, rowNum) -> new DelResUserRes(
                        rs.getString("name"),
                        rs.getString("nickName"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("status")),
                deleteUserEmailParams);
    }
}
