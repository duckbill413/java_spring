package com.example.workbook.service;

import com.example.workbook.domain.APIUser;
import com.example.workbook.dto.APIUserDTO;
import com.example.workbook.repository.APIUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * author        : duckbill413
 * date          : 2023-03-09
 * description   :
 **/
@Service
@Log4j2
@RequiredArgsConstructor
public class APIUserServiceImpl implements APIUserService{
    private final APIUserRepository apiUserRepository;
    private final ModelMapper modelMapper;
    @Override
    public String register(APIUserDTO apiUserDTO) {
        APIUser apiUser = modelMapper.map(apiUserDTO, APIUser.class);

        return apiUserRepository.save(apiUser).getMid();
    }

    @Override
    public APIUserDTO read(String mid) {
        Optional<APIUser> result = apiUserRepository.findByMid(mid);
        APIUser apiUser = result.orElseThrow();

        return modelMapper.map(apiUser, APIUserDTO.class);
    }

    @Override
    public void modify(String mid, APIUserDTO apiUserDTO) {
        Optional<APIUser> result = apiUserRepository.findByMid(mid);
        APIUser apiUser = result.orElseThrow();
        apiUser.setEmail(apiUserDTO.getEmail());
        apiUser.setDeleted(apiUser.isDeleted());
        apiUser.setSocial(apiUser.isSocial());

        apiUserRepository.save(apiUser);
    }

    @Override
    public void remove(String mid) {
        apiUserRepository.deleteById(mid);
    }
}
