package com.example.rising.src.user;

import com.example.rising.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 회원가입
    public Long createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into users (email, password, phone_number, address, nickname, profile_img) VALUES (?,?,?,?,?,?)"; // 실행될 동적 쿼리문
        Object[] createUserParams = new Object[]{postUserReq.getEmail(), postUserReq.getPassword(),
                postUserReq.getPhoneNumber(), postUserReq.getAddress(),postUserReq.getNickname(), postUserReq.getProfileImg()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값을 가져온다.
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환한다.
    }

    // 이메일 중복 확인
    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select email from users where email = ?)"; // User Table에 해당 email 값을 갖는 유저 정보가 존재하는가?
        String checkEmailParams = email; // 해당(확인할) 이메일 값
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams); // checkEmailQuery, checkEmailParams를 통해 가져온 값(intgud)을 반환한다. -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환됩니다.
    }

    // 닉네임 중복 확인
    public int checkNickname(String nickname){
        String checkNicknameQuery = "select exists(select nickname from users where nickname = ?)";
        String checkNicknameParam = nickname;
        return this.jdbcTemplate.queryForObject(checkNicknameQuery, int.class, checkNicknameParam);
    }

    // 비밀번호 변경
    public int modifyUserPwd(Long usersIdx, String newPassword) {
        String modifyUserNameQuery = "update users set password = ? where users_idx = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyUserNameParams = new Object[]{newPassword, usersIdx}; // 주입될 값들(nickname, usersIdx) 순

        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0) 
    }


    public Users getPwd(PostLoginReq postLoginReq) {
        String getPwdQuery = "select users_idx, email, password, phone_number, address, nickname, profile_img from users where email = ?"; // 해당 email을 만족하는 User의 정보들을 조회한다.
        String getPwdParams = postLoginReq.getEmail(); // 주입될 email값을 클라이언트의 요청에서 주어진 정보를 통해 가져온다.

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> Users.builder()
                        .usersIdx(rs.getLong("users_idx"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .phoneNumber(rs.getString("phone_number"))
                        .address(rs.getString("address"))
                        .nickname(rs.getString("nickname"))
                        .profileImg(rs.getString("profile_img"))
                        .build(),
                getPwdParams
        );
    }

    // 해당 userIdx를 갖는 유저조회
    public Users getUser(Long usersIdx) {
        String getUserQuery = "select * from users where users_idx = ?"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        long getUserParams = usersIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> Users.builder()
                        .usersIdx(rs.getLong("users_idx"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .phoneNumber(rs.getString("phone_number"))
                        .address(rs.getString("address"))
                        .nickname(rs.getString("nickname"))
                        .profileImg(rs.getString("profile_img"))
                        .build(),
                getUserParams);
    }
}
