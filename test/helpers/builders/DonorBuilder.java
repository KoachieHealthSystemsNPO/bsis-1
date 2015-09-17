package helpers.builders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.donor.Donor;
import model.util.Gender;

public class DonorBuilder extends AbstractEntityBuilder<Donor> {
    
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date birthDate;
    private String notes;
    private Boolean deleted;
    private Date dateOfFirstDonation;
    private Date dateOfLastDonation;

    public DonorBuilder withId(Long id) {
        this.id = id;
        return this;
    }
    
    public DonorBuilder withNotes(String notes) {
        this.notes = notes;
        return this;
    }
    
    public DonorBuilder thatIsDeleted() {
        deleted = true;
        return this;
    }
    
    public DonorBuilder withDateOfFirstDonation(Date dateOfFirstDonation) {
        this.dateOfFirstDonation = dateOfFirstDonation;
        return this;
    }
    
    public DonorBuilder withDateOfLastDonation(Date dateOfLastDonation) {
        this.dateOfLastDonation = dateOfLastDonation;
        return this;
    }

	public DonorBuilder withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public DonorBuilder withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public DonorBuilder withGender(Gender gender) {
		this.gender = gender;
		return this;
	}

	public DonorBuilder withBirthDate(Date birthDate) {
		this.birthDate = birthDate;
		return this;
	}

	public DonorBuilder withBirthDate(String dateOfBirth) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.birthDate = sdf.parse(dateOfBirth);
		return this;
	}

    @Override
    public Donor build() {
        Donor donor = new Donor();
        donor.setId(id);
        donor.setFirstName(firstName);
        donor.setLastName(lastName);
        donor.setGender(gender);
        donor.setBirthDate(birthDate);
        donor.setNotes(notes);
        donor.setIsDeleted(deleted);
        donor.setDateOfFirstDonation(dateOfFirstDonation);
        donor.setDateOfLastDonation(dateOfLastDonation);
        return donor;
    }
    
    public static DonorBuilder aDonor() {
        return new DonorBuilder();
    }

}
