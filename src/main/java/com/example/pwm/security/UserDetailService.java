package com.example.pwm.security;

import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.pwm.domain.host.HostDTO;
import com.example.pwm.domain.host.HostRepository;
import com.example.pwm.domain.host.Host;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final HostRepository hostRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("---------------loadUserByUsername-----------------{}", username);

        Host host = hostRepository.getWithRoles(username);

        if (host == null) {
            throw new UsernameNotFoundException("Not Found");
        }

        HostDTO hostDTO = new HostDTO(
                host.getEmail(),
                host.getPasswd(),
                host.getName(),
                host.getHostRoleList()
                        .stream().map(hostRole -> hostRole.name()).collect(Collectors.toList()));

        log.info("hostDTO: {}", hostDTO);
        return hostDTO;
    }
}
