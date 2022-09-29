package com.example.beommin.domain.user.entity;

import static org.apache.logging.log4j.util.Strings.isBlank;

import com.example.beommin.common.entity.BaseEntity;
import com.example.beommin.domain.store.entity.Store;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "users")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "provider")
    private String provider;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "name")
    private String name;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Store> stores = new ArrayList<>();

    @Column(name = "refresh_token")
    private String refreshToken;

    public void changeEmail(String email) {
        if(checkBlank(email)) setEmail(email);
        else throw new IllegalArgumentException();
    }

    public void changeProfileUrl(String profileUrl) {
        if(checkBlank(profileUrl)) setProfileUrl(profileUrl);
        else throw new IllegalArgumentException();
    }

    public void changeName(String name) {
        if(checkBlank(name)) setName(name);
        else throw new IllegalArgumentException();
    }

    private boolean checkBlank(String target) {
        if(isBlank(target)) return false;
        return true;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setRole(Role role) {
        this.role = role;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
