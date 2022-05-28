package comicsbroker.image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "broker_image")
public class Image {

	@Id
	@Column(name = "image_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imageID;
	
	@Column(name = "image_status")
	private Integer imageStatus;

	@Column(name = "image_icon_URL")
	private String imageIconURL;
	
	@Column(name = "image_medium_URL")
	private String imageMediumURL;
	
	@Column(name = "image_screen_URL")
	private String imageScreenURL;
	
	@Column(name = "image_screen_large_URL")
	private String imageScreenLargeURL;
	
	@Column(name = "image_small_URL")
	private String imageSmallURL;
	
	@Column(name = "image_super_URL")
	private String imageSuperURL;
	
	@Column(name = "image_thumb_URL")
	private String imageThumbURL;
	
	@Column(name = "image_tiny_URL")
	private String imageTinyURL;
	
	@Column(name = "image_original_URL")
	private String imageOriginalURL;

	

	
	@Column(name = "image_icon")
	private String imageIcon;
	
	@Column(name = "image_medium")
	private String imageMedium;
	
	@Column(name = "image_screen")
	private String imageScreen;
	
	@Column(name = "image_screen_large")
	private String imageScreenLarge;
	
	@Column(name = "image_small")
	private String imageSmall;
	
	@Column(name = "image_super")
	private String imageSuper;
	
	@Column(name = "image_thumb")
	private String imageThumb;
	
	@Column(name = "image_tiny")
	private String imageTiny;
	
	@Column(name = "image_original")
	private String imageOriginal;
	
	public Integer getImageStatus() {
		return imageStatus;
	}
	
	public Long getImageID() {
		return imageID;
	}

	public String getImageIconURL() {
		return imageIconURL;
	}

	public String getImageMediumURL() {
		return imageMediumURL;
	}

	public String getImageScreenURL() {
		return imageScreenURL;
	}

	public String getImageScreenLargeURL() {
		return imageScreenLargeURL;
	}

	public String getImageSmallURL() {
		return imageSmallURL;
	}

	public String getImageSuperURL() {
		return imageSuperURL;
	}

	public String getImageThumbURL() {
		return imageThumbURL;
	}

	public String getImageTinyURL() {
		return imageTinyURL;
	}

	public String getImageOriginalURL() {
		return imageOriginalURL;
	}

	public String getImageIcon() {
		return imageIcon;
	}

	public String getImageMedium() {
		return imageMedium;
	}

	public String getImageScreen() {
		return imageScreen;
	}

	public String getImageScreenLarge() {
		return imageScreenLarge;
	}

	public String getImageSmall() {
		return imageSmall;
	}

	public String getImageSuper() {
		return imageSuper;
	}

	public String getImageThumb() {
		return imageThumb;
	}

	public String getImageTiny() {
		return imageTiny;
	}

	public String getImageOriginal() {
		return imageOriginal;
	}

	
	public void setImageID(Long imageID) {
		this.imageID = imageID;
	}

	public void setImageStatus(Integer imageStatus) {
		this.imageStatus = imageStatus;
	}

	@XmlElement(name = "icon_url")
	public void setImageIconURL(String imageIconURL) {
		this.imageIconURL = imageIconURL;
	}
	
	@XmlElement(name = "medium_url")
	public void setImageMediumURL(String imageMediumURL) {
		this.imageMediumURL = imageMediumURL;
	}

	@XmlElement(name = "screen_url")
	public void setImageScreenURL(String imageScreenURL) {
		this.imageScreenURL = imageScreenURL;
	}

	@XmlElement(name = "screen_large_url")
	public void setImageScreenLargeURL(String imageScreenLargeURL) {
		this.imageScreenLargeURL = imageScreenLargeURL;
	}

	@XmlElement(name = "small_url")
	public void setImageSmallURL(String imageSmallURL) {
		this.imageSmallURL = imageSmallURL;
	}

	@XmlElement(name = "super_url")
	public void setImageSuperURL(String imageSuperURL) {
		this.imageSuperURL = imageSuperURL;
	}

	@XmlElement(name = "thumb_url")
	public void setImageThumbURL(String imageThumbURL) {
		this.imageThumbURL = imageThumbURL;
	}

	@XmlElement(name = "tiny_url")
	public void setImageTinyURL(String imageTinyURL) {
		this.imageTinyURL = imageTinyURL;
	}

	@XmlElement(name = "original_url")
	public void setImageOriginalURL(String imageOriginalURL) {
		this.imageOriginalURL = imageOriginalURL;
	}

	public void setImageIcon(String imageIcon) {
		this.imageIcon = imageIcon;
	}

	public void setImageMedium(String imageMedium) {
		this.imageMedium = imageMedium;
	}

	public void setImageScreen(String imageScreen) {
		this.imageScreen = imageScreen;
	}

	public void setImageScreenLarge(String imageScreenLarge) {
		this.imageScreenLarge = imageScreenLarge;
	}

	public void setImageSmall(String imageSmall) {
		this.imageSmall = imageSmall;
	}

	public void setImageSuper(String imageSuper) {
		this.imageSuper = imageSuper;
	}

	public void setImageThumb(String imageThumb) {
		this.imageThumb = imageThumb;
	}

	public void setImageTiny(String imageTiny) {
		this.imageTiny = imageTiny;
	}

	public void setImageOriginal(String imageOriginal) {
		this.imageOriginal = imageOriginal;
	}

	
}
