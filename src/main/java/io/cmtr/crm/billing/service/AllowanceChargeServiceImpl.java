package io.cmtr.crm.billing.service;

import io.cmtr.crm.billing.model.invoice.AllowanceCharge;
import io.cmtr.crm.billing.model.invoice.InvoiceLineItem;
import io.cmtr.crm.billing.repository.AllowanceChargeRepository;
import io.cmtr.crm.customer.model.BillingAccount;
import io.cmtr.crm.customer.model.Supplier;
import io.cmtr.crm.shared.generic.repository.GenericRepository;
import io.cmtr.crm.shared.generic.service.GenericService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;


/**
 * Allowance Charge Service Implementation
 *
 * @author Harald Blikø
 */
@Service
public class AllowanceChargeServiceImpl extends GenericService<Long, AllowanceCharge> implements AllowanceChargeService {



    /**
     *
     * @param repository
     */
    public AllowanceChargeServiceImpl(AllowanceChargeRepository repository) {
        super(repository);
    }



    /**
     *
     * @param supplier
     * @param billingAccount
     * @param isAllocated
     * @param periodEnd
     * @return
     */
    @Override
    public Set<InvoiceLineItem> getLineItems(
            Supplier supplier,
            BillingAccount billingAccount,
            Optional<Boolean> isAllocated,
            ZonedDateTime periodEnd
    ) {
        // Get AllowanceCharges that
        // - is LineItem type
        // - allocated - true/false/any
        // - a start date before period and
        // - SupplierId
        // - BillingAccountId
        // TODO - Update repository
        return Collections.emptySet();
    }



}
