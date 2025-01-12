package com.planverse.server.team.dto

import com.planverse.server.team.entity.TeamMemberInfoEntity

data class TeamMemberInfoDTO(
    var id: Long? = null,
    var userInfoId: Long,
    var teamInfoId: Long,
    var creator: Boolean,
    var username: String? = null,
) {
    companion object {
        fun toDto(teamMemberInfoEntity: TeamMemberInfoEntity): TeamMemberInfoDTO {
            return TeamMemberInfoDTO(
                teamMemberInfoEntity.id,
                teamMemberInfoEntity.userInfoId,
                teamMemberInfoEntity.teamInfoId,
                teamMemberInfoEntity.creator,
            )
        }

        fun toDto(teamMemberInfoEntity: TeamMemberInfoEntity, username: String): TeamMemberInfoDTO {
            return TeamMemberInfoDTO(
                teamMemberInfoEntity.id,
                teamMemberInfoEntity.userInfoId,
                teamMemberInfoEntity.teamInfoId,
                teamMemberInfoEntity.creator,
                username
            )
        }

        fun toEntity(userInfoId: Long, teamInfoId: Long, creator: Boolean): TeamMemberInfoEntity {
            return TeamMemberInfoEntity(
                userInfoId = userInfoId,
                teamInfoId = teamInfoId,
                creator = creator,
            )
        }
    }
}