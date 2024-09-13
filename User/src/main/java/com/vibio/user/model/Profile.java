/*
 *  Profile
 *  @author: Minhhieuano
 *  @created 9/8/2024 8:48 PM
 * */

package com.vibio.user.model;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Profile {

	@Id
	@Setter(AccessLevel.PRIVATE)
	private String id = NanoIdUtils.randomNanoId();
}
