package capstone.is4103capstone.admin.repository;

import capstone.is4103capstone.entities.SessionKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionKeyRepo extends JpaRepository<SessionKey, String> {

    public SessionKey findSessionKeyBySessionKey(String sessionKey);

}
