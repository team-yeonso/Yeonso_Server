package com.yenso.yensoserver.Repository;

import com.yenso.yensoserver.Domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepo extends JpaRepository<Job, Long> {
}
