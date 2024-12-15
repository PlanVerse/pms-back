package com.planverse.server.project.entity

import com.planverse.server.common.entity.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "assign_info", schema = "public")
class AssignInfoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @NotNull
    @Column(name = "workflow_info_id", nullable = false)
    var workflowInfoId: Long,

    @NotNull
    @Column(name = "user_info_id", nullable = false)
    var userInfoId: Long,
) : BaseEntity()