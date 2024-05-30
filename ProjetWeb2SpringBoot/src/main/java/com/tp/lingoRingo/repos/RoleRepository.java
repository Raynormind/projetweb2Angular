package com.tp.lingoRingo.repos;

import com.tp.lingoRingo.entities.Forum;
import com.tp.lingoRingo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {

}
