package io.github.arkaman.taskmanager.service;

import io.github.arkaman.taskmanager.domain.dto.AuthResponse;
import io.github.arkaman.taskmanager.domain.dto.LoginRequest;
import io.github.arkaman.taskmanager.domain.dto.RegisterRequest;
import io.github.arkaman.taskmanager.domain.entity.AppUser;
import io.github.arkaman.taskmanager.domain.entity.RefreshToken;
import io.github.arkaman.taskmanager.domain.entity.Role;
import io.github.arkaman.taskmanager.security.TokenType;
import io.github.arkaman.taskmanager.repository.RefreshTokenRepository;
import io.github.arkaman.taskmanager.repository.UserRepository;
import io.github.arkaman.taskmanager.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepo;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    public AuthService(UserRepository repo,
                       PasswordEncoder encoder,
                       JwtService jwtService,
                       RefreshTokenRepository refreshTokenRepo) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.refreshTokenRepo = refreshTokenRepo;
    }

    @Transactional
    public void register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();
        String username = request.username().trim();
        String password = request.password();
        if (repo.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (repo.findByEmailIgnoreCase(email).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        AppUser user = AppUser.builder()
                .username(username)
                .email(email)
                .password(encoder.encode(password))
                .role(Role.USER)
                .build();
        repo.save(user);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {

        String email = request.email().trim().toLowerCase();
        String password = request.password();

        AppUser user = repo.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String accessToken = jwtService.generateAccessToken(email);
        String refreshToken = jwtService.generateRefreshToken(email);

        refreshTokenRepo.deleteByUser(user);

        RefreshToken entity = RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .expiry(Instant.now().plusMillis(refreshExpiration))
                .build();

        refreshTokenRepo.save(entity);

        return new AuthResponse(accessToken, refreshToken);
    }

    @Transactional
    public AuthResponse refresh(String refreshToken) {

        TokenType type = jwtService.extractTokenType(refreshToken);

        if (type != TokenType.REFRESH) {
            throw new IllegalArgumentException("Invalid token type");
        }

        RefreshToken token = refreshTokenRepo.findByToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (token.getExpiry().isBefore(Instant.now())) {
            refreshTokenRepo.delete(token);
            throw new IllegalArgumentException("Refresh token expired");
        }

        AppUser user = token.getUser();

        refreshTokenRepo.delete(token);

        String newAccessToken = jwtService.generateAccessToken(user.getEmail());
        String newRefreshToken = jwtService.generateRefreshToken(user.getEmail());

        RefreshToken newEntity = RefreshToken.builder()
                .token(newRefreshToken)
                .user(user)
                .expiry(Instant.now().plusMillis(refreshExpiration))
                .build();

        refreshTokenRepo.save(newEntity);

        return new AuthResponse(newAccessToken, newRefreshToken);
    }

    @Transactional
    public void logout(AppUser user) {
        refreshTokenRepo.deleteByUser(user);
    }
}