package io.fourfinanceit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * Loan extentions modify a loan
 */
@Entity
public class LoanExtension {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @JsonIgnore
    private Date created = new Date();

    @NotNull
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @JsonIgnore
    private Date updated = new Date();

    @ManyToOne
    private Loan loan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LoanExtension startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LoanExtension endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Loan getLoan() {
        return loan;
    }

    public LoanExtension loan(Loan loan) {
        this.loan = loan;
        return this;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoanExtension loanExtension = (LoanExtension) o;
        if (loanExtension.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, loanExtension.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LoanExtension{" +
                "id=" + id +
                ", startDate='" + startDate + "'" +
                ", endDate='" + endDate + "'" +
                ", create='" + created + "'" +
                ", updated='" + updated + "'" +
                '}';
    }
}
