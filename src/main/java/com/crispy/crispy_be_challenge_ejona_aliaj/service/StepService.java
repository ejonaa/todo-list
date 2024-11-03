package com.crispy.crispy_be_challenge_ejona_aliaj.service;

import com.crispy.crispy_be_challenge_ejona_aliaj.dto.StepDTO;

public interface StepService {

    StepDTO createStep(Long projectId, StepDTO stepDTO);
}
