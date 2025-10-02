package vn.iotstar.repository;
import vn.iotstar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // tìm user theo email (để login)
    Optional<User> findByEmail(String email);

    // nếu cần: kiểm tra tồn tại email
    boolean existsByEmail(String email);
}
