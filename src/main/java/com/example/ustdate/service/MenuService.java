package com.example.ustdate.service;

import com.example.ustdate.responseDTO.InterMediateResponseDTO;

public interface MenuService {

	InterMediateResponseDTO suggestion(InterMediateResponseDTO message);

	InterMediateResponseDTO newChat(InterMediateResponseDTO message);

	InterMediateResponseDTO chat(InterMediateResponseDTO message);

}
