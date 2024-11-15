/*
 *  Accountrepository
 *  @author: Minhhieuano
 *  @created 9/8/2024 9:29 PM
 * */

package com.vibio.user.repository;

import com.vibio.user.model.Account;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
	boolean existsByEmail(String email);

	List<Account> findAllByIdIn(List<String> ids);

	Optional<Account> findByEmail(String email);
}
