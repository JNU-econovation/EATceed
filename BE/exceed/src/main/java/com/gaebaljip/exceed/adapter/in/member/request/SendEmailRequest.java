package com.gaebaljip.exceed.adapter.in.member.request;

import javax.validation.constraints.Email;

import com.gaebaljip.exceed.common.ValidationMessage;

public record SendEmailRequest(@Email(message = ValidationMessage.INVALID_EMAIL) String email) {}
