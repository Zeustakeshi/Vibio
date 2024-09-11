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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@Table(indexes = @Index(columnList = "email"))
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account implements UserDetails {

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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
				.collect(Collectors.toSet());
	}
}
