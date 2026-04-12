package models.clubs;

public record CreateClubsBodyModel(String bookTitle,
                                   String bookAuthors,
                                   Integer publicationYear,
                                   String description,
                                   String telegramChatLink) {
}
