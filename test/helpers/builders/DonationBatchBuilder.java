package helpers.builders;

import java.util.List;

import model.donation.Donation;
import model.donationbatch.DonationBatch;

public class DonationBatchBuilder extends AbstractEntityBuilder<DonationBatch> {

	private Integer id;
	private String batchNumber;
    private List<Donation> donations;
    private boolean deleted;
    private boolean closed;

    public DonationBatchBuilder withDonations(List<Donation> donations) {
        this.donations = donations;
        return this;
    }
    
    public DonationBatchBuilder thatIsDeleted() {
        deleted = true;
        return this;
    }
    
    public DonationBatchBuilder thatIsClosed() {
        closed = true;
        return this;
    }
    
    public DonationBatchBuilder withId(Integer id) {
    	this.id = id;
    	return this;
    }

    public DonationBatchBuilder withBatchNumber(String batchNumber) {
    	this.batchNumber = batchNumber;
    	return this;
    }

    @Override
    public DonationBatch build() {
        DonationBatch donationBatch = new DonationBatch();
        donationBatch.setId(id);
        donationBatch.setBatchNumber(batchNumber);
        donationBatch.setDonation(donations);
        donationBatch.setIsDeleted(deleted);
        donationBatch.setIsClosed(closed);
        return donationBatch;
    }
    
    public static DonationBatchBuilder aDonationBatch() {
        return new DonationBatchBuilder();
    }

}
