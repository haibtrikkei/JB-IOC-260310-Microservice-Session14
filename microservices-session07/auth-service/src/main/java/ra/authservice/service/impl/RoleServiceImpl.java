package ra.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ra.authservice.entity.Role;
import ra.authservice.repository.RoleRepository;
import ra.authservice.service.RoleService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(()-> new NoSuchElementException("Không tồn tại role "+roleId));
    }

    @Override
    public Role insert(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(Long roleId, Role role) {
        roleRepository.findById(roleId).orElseThrow(()-> new NoSuchElementException("Không tồn tại role "+roleId));
        role.setRoleId(roleId);
        return roleRepository.save(role);
    }

    @Override
    public void deleteById(Long roleId) {
        roleRepository.findById(roleId).orElseThrow(()-> new NoSuchElementException("Không tồn tại role "+roleId));
        roleRepository.deleteById(roleId);
    }
}
