package hu.ait.bookapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Doc(
    @SerialName("already_read_count")
    var alreadyReadCount: Int? = null,
    @SerialName("author_alternative_name")
    var authorAlternativeName: List<String?>? = null,
    @SerialName("author_facet")
    var authorFacet: List<String?>? = null,
    @SerialName("author_key")
    var authorKey: List<String?>? = null,
    @SerialName("author_name")
    var authorName: List<String?>? = null,
    @SerialName("contributor")
    var contributor: List<String?>? = null,
    @SerialName("cover_edition_key")
    var coverEditionKey: String? = null,
    @SerialName("cover_i")
    var coverI: Int? = null,
    @SerialName("currently_reading_count")
    var currentlyReadingCount: Int? = null,
    @SerialName("ddc")
    var ddc: List<String?>? = null,
    @SerialName("ddc_sort")
    var ddcSort: String? = null,
    @SerialName("ebook_access")
    var ebookAccess: String? = null,
    @SerialName("ebook_count_i")
    var ebookCountI: Int? = null,
    @SerialName("edition_count")
    var editionCount: Int? = null,
    @SerialName("edition_key")
    var editionKey: List<String?>? = null,
    @SerialName("first_publish_year")
    var firstPublishYear: Int? = null,
    @SerialName("first_sentence")
    var firstSentence: List<String?>? = null,
    @SerialName("has_fulltext")
    var hasFulltext: Boolean? = null,
    @SerialName("ia")
    var ia: List<String?>? = null,
    @SerialName("ia_box_id")
    var iaBoxId: List<String?>? = null,
    @SerialName("ia_collection")
    var iaCollection: List<String?>? = null,
    @SerialName("ia_collection_s")
    var iaCollectionS: String? = null,
    @SerialName("ia_loaded_id")
    var iaLoadedId: List<String?>? = null,
    @SerialName("id_alibris_id")
    var idAlibrisId: List<String?>? = null,
    @SerialName("id_amazon")
    var idAmazon: List<String?>? = null,
    @SerialName("id_amazon_ca_asin")
    var idAmazonCaAsin: List<String?>? = null,
    @SerialName("id_amazon_co_uk_asin")
    var idAmazonCoUkAsin: List<String?>? = null,
    @SerialName("id_amazon_de_asin")
    var idAmazonDeAsin: List<String?>? = null,
    @SerialName("id_amazon_it_asin")
    var idAmazonItAsin: List<String?>? = null,
    @SerialName("id_bcid")
    var idBcid: List<String?>? = null,
    @SerialName("id_better_world_books")
    var idBetterWorldBooks: List<String?>? = null,
    @SerialName("id_bibliothèque_nationale_de_france")
    var idBibliothèqueNationaleDeFrance: List<String?>? = null,
    @SerialName("id_british_library")
    var idBritishLibrary: List<String?>? = null,
    @SerialName("id_british_national_bibliography")
    var idBritishNationalBibliography: List<String?>? = null,
    @SerialName("id_canadian_national_library_archive")
    var idCanadianNationalLibraryArchive: List<String?>? = null,
    @SerialName("id_depósito_legal")
    var idDepósitoLegal: List<String?>? = null,
    @SerialName("id_goodreads")
    var idGoodreads: List<String?>? = null,
    @SerialName("id_google")
    var idGoogle: List<String?>? = null,
    @SerialName("id_hathi_trust")
    var idHathiTrust: List<String?>? = null,
    @SerialName("id_librarything")
    var idLibrarything: List<String?>? = null,
    @SerialName("id_libris")
    var idLibris: List<String?>? = null,
    @SerialName("id_nla")
    var idNla: List<String?>? = null,
    @SerialName("id_overdrive")
    var idOverdrive: List<String?>? = null,
    @SerialName("id_paperback_swap")
    var idPaperbackSwap: List<String?>? = null,
    @SerialName("id_scribd")
    var idScribd: List<String?>? = null,
    @SerialName("id_wikidata")
    var idWikidata: List<String?>? = null,
    @SerialName("isbn")
    var isbn: List<String?>? = null,
    @SerialName("key")
    var key: String? = null,
    @SerialName("language")
    var language: List<String?>? = null,
    @SerialName("last_modified_i")
    var lastModifiedI: Int? = null,
    @SerialName("lcc")
    var lcc: List<String?>? = null,
    @SerialName("lcc_sort")
    var lccSort: String? = null,
    @SerialName("lccn")
    var lccn: List<String?>? = null,
    @SerialName("lending_edition_s")
    var lendingEditionS: String? = null,
    @SerialName("lending_identifier_s")
    var lendingIdentifierS: String? = null,
    @SerialName("number_of_pages_median")
    var numberOfPagesMedian: Int? = null,
    @SerialName("oclc")
    var oclc: List<String?>? = null,
    @SerialName("person")
    var person: List<String?>? = null,
    @SerialName("person_facet")
    var personFacet: List<String?>? = null,
    @SerialName("person_key")
    var personKey: List<String?>? = null,
    @SerialName("place")
    var place: List<String?>? = null,
    @SerialName("place_facet")
    var placeFacet: List<String?>? = null,
    @SerialName("place_key")
    var placeKey: List<String?>? = null,
    @SerialName("printdisabled_s")
    var printdisabledS: String? = null,
    @SerialName("public_scan_b")
    var publicScanB: Boolean? = null,
    @SerialName("publish_date")
    var publishDate: List<String?>? = null,
    @SerialName("publish_place")
    var publishPlace: List<String?>? = null,
    @SerialName("publish_year")
    var publishYear: List<Int?>? = null,
    @SerialName("publisher")
    var publisher: List<String?>? = null,
    @SerialName("publisher_facet")
    var publisherFacet: List<String?>? = null,
    @SerialName("ratings_average")
    var ratingsAverage: Double? = null,
    @SerialName("ratings_count")
    var ratingsCount: Int? = null,
    @SerialName("ratings_count_1")
    var ratingsCount1: Int? = null,
    @SerialName("ratings_count_2")
    var ratingsCount2: Int? = null,
    @SerialName("ratings_count_3")
    var ratingsCount3: Int? = null,
    @SerialName("ratings_count_4")
    var ratingsCount4: Int? = null,
    @SerialName("ratings_count_5")
    var ratingsCount5: Int? = null,
    @SerialName("ratings_sortable")
    var ratingsSortable: Double? = null,
    @SerialName("readinglog_count")
    var readinglogCount: Int? = null,
    @SerialName("seed")
    var seed: List<String?>? = null,
    @SerialName("subject")
    var subject: List<String?>? = null,
    @SerialName("subject_facet")
    var subjectFacet: List<String?>? = null,
    @SerialName("subject_key")
    var subjectKey: List<String?>? = null,
    @SerialName("subtitle")
    var subtitle: String? = null,
    @SerialName("time")
    var time: List<String?>? = null,
    @SerialName("time_facet")
    var timeFacet: List<String?>? = null,
    @SerialName("time_key")
    var timeKey: List<String?>? = null,
    @SerialName("title")
    var title: String? = null,
    @SerialName("title_sort")
    var titleSort: String? = null,
    @SerialName("title_suggest")
    var titleSuggest: String? = null,
    @SerialName("type")
    var type: String? = null,
    @SerialName("_version_")
    var version: Long? = null,
    @SerialName("want_to_read_count")
    var wantToReadCount: Int? = null
)