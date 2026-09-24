package ra.authservice.service;


import ra.authservice.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role>  findAll();
    Role findById(Long roleId);
    Role insert(Role role);
    Role update(Long roleId, Role role);
    void deleteById(Long roleId);
}
