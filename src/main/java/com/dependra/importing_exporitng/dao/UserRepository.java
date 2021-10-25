package com.dependra.importing_exporitng.dao;

import com.dependra.importing_exporitng.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "Select file_location from user where id =:id",nativeQuery = true)
    public String getFileLocation(@Param(value = "id")int id);
}
