/*
 *  Account
 *  @author: Minhhieuano
 *  @created 9/8/2024 8:45 PM
 * */

package com.vibio.user.model;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.vibio.user.common.enums.Role;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@Table(indexes = @Index(columnList = "email"))
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

	@Id
	@Builder.Default
	@Setter(AccessLevel.PRIVATE)
	private String id = NanoIdUtils.randomNanoId();

	@Column(unique = true)
	private String email;

	private String username;

	private String password;

	@Enumerated(EnumType.STRING)
	@ElementCollection
	private List<Role> roles;

	@Builder.Default
	private Boolean mfa = false;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Profile profile;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
