package efub.session.blog.domain.auth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import efub.session.blog.domain.auth.entity.JwtToken;

@Repository
public interface JwtRepository extends CrudRepository<JwtToken, String> {

	Optional<JwtToken> findByAccessToken(String accessToken);
}
