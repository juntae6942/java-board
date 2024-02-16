package cnu.likelion.board.member.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record MemberSignupRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String name
) {
}
