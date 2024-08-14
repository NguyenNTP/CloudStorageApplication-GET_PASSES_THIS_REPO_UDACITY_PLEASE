package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid= #{userId}")
    List<Credential> getCredentials(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredentialById(Integer credentialId);


    @Insert("INSERT INTO CREDENTIALS (url, username, encryptionkey, password, userid) " +
            "VALUES (#{url}, #{username}, #{encryptionKey}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);


    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, encryptionkey = #{encryptionKey}, password = #{password} WHERE credentialid = #{credentialId}")
    int editCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    void deleteCredential(Integer credentialId);
}
