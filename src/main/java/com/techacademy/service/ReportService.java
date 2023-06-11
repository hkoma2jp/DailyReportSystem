package com.techacademy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository repository) {
        this.reportRepository = repository;
    }

    /** 全件検索結果を返す */
    public List<Report> getReportList() {
        return  reportRepository.findAll();
    }

    /** 特定employeeの検索結果を返す */
    public List<Report> getEmployeeReportList(Employee employee) {
        return  reportRepository.findByEmployee(employee);
    }

    /** Employeeを1件検索して返す */
    public Report getReport(Integer id) {
        return reportRepository.findById(id).get();
    }

    /** Reportの登録を行う */
    @Transactional
    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

}
