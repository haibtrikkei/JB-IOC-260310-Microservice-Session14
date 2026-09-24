package ra.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.authservice.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
