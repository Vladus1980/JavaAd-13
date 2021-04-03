package domain;


	
	import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


	
	@Entity
	@Table(name="magazine")
	
	public class Magazine {
		@Id
		@GeneratedValue
		private int id;
		@Column(name="title")
		private String title;
		@Column(name="description")
		private String description;
		@Column(name="publish_date")
		private LocalDate publishDate;
		@Column(name="subscribe_price")
		private int subscribePrice;
		
		public Magazine() { }

		public Magazine(int id, String title, String description, LocalDate publishDate, int subscribePrice) {
			this.id = id;
			this.title = title;
			this.description = description;
			this.publishDate = publishDate;
			this.subscribePrice = subscribePrice;
		}

		public Magazine(String title, String description, LocalDate publishDate, int subscribePrice) {
			this.title = title;
			this.description = description;
			this.publishDate = publishDate;
			this.subscribePrice = subscribePrice;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public LocalDate getPublishDate() {
			return publishDate;
		}

		public void setPublishDate(LocalDate publishDate) {
			this.publishDate = publishDate;
		}

		public int getSubscribePrice() {
			return subscribePrice;
		}

		public void setSubscribePrice(int subscribePrice) {
			this.subscribePrice = subscribePrice;
		}

		@Override
		public String toString() {
			if (id == 0)
				return "Журнал \"" + title + "\", " + description + ", Дата публікації: " + publishDate + ", Вартість підписки: "
						+ subscribePrice / 100 + "." + String.format("%02d", subscribePrice % 100) + " грн.";
			else
				return "Magazine ID#" + id + ": Журнал \"" + title + "\", " + description + ", Дата публікації: " + publishDate
						+ ", Вартість підписки: " + subscribePrice / 100 + "." + String.format("%02d", subscribePrice % 100)
						+ " грн.";
		}
	}


