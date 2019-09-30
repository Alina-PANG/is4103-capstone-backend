package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.SessionKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionKeyRepo extends JpaRepository<SessionKey, String> {

    SessionKey findSessionKeyBySessionKey(String sessionKey);

    void deleteSessionKeysByLinkedUser_UserName(String userName);


}
