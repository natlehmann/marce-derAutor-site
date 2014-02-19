CREATE TABLE CollectionDetailsGroup(
	CollectionDetailsGroupID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	CompaniesID int NULL,
	CompaniesID_ToTransfer int NULL,
	CollectionDetailsGroupID_SendTransTo int NULL,
	CollectShare numeric(7, 3) NULL,
	CollectShare_Calculated numeric(7, 3) NULL,
	CollectShare_Excedent numeric(7, 3) NULL,
	TransferShare numeric(7, 3) NULL,
	CollectShare_ExcedentSolved numeric(7, 3) NOT NULL DEFAULT 0
) ENGINE=InnoDB;

CREATE TABLE Sources(
	SourcesID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	CountriesID int NULL,
	SourcesTypesID int NULL,
	SourcesID_Parent int NULL,
	SourcesCode varchar(50) NULL
) ENGINE=InnoDB;


CREATE TABLE AuthorSocieties(
	AuthorSocietiesID BIGINT NOT NULL PRIMARY KEY,
	AuthorSocietyName varchar(30) NULL,
	Biem varchar(1) NULL,
	RealName varchar(200) NULL,
	Address varchar(200) NULL,
	Phone char(100) NULL,
	Email char(50) NULL,
	Web char(100) NULL,
	SocietyCode int NULL,
	AuthorSocietyExternalCode varchar(3) NULL,
	FOREIGN KEY (AuthorSocietiesID) REFERENCES Sources(SourcesID)
) ENGINE=InnoDB;


CREATE TABLE Customers(
	CustomersID BIGINT NOT NULL PRIMARY KEY,
	CustomerName varchar(50) NULL,
	AcceptsPRG varchar(1) NULL,
	FOREIGN KEY (CustomersID) REFERENCES Sources(SourcesID)
) ENGINE=InnoDB;

CREATE TABLE Countries(
	CountriesID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ISOCountryCode varchar(10) NULL,
	CountryName varchar(50) NULL,
	OfficialCountryName varchar(100) NULL,
	CurrenciesID int NULL,
	Nationality varchar(50) NULL,
	NumericTISCode int NULL,
	FlagIMG varchar(255) NULL,
	AuthorPerfCollectFactor smallint NULL DEFAULT 1,
	NOPRCode varchar(10) NULL
) ENGINE=InnoDB;

CREATE TABLE Owners(
	OwnersID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	CountriesID BIGINT NULL,
	CAE_IPI_Number varchar(20) NULL,
	FOREIGN KEY (CountriesID) REFERENCES Countries(CountriesID)
) ENGINE=InnoDB;

CREATE TABLE Performers(
	PerformersID BIGINT NOT NULL PRIMARY KEY,
	PerformerName varchar(200) NULL,
	Responsible varchar(50) NULL,
	ConformationDate datetime NULL,
	DisolutionDate datetime NULL,
	FOREIGN KEY (PerformersID) REFERENCES Owners(OwnersID)
) ENGINE=InnoDB;

CREATE TABLE UsageUnlinkedWorks(
	UsageUnlinkedWorksID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	SongTitle varchar(100) NOT NULL,
	SongSubtitle varchar(100) NOT NULL,
	SongPerformer varchar(100) NOT NULL,
	PerformersID BIGINT NULL,
	SongPublisher varchar(100) NULL,
	SongOwners varchar(500) NOT NULL,
	SongAux varchar(100) NULL,
	TitlePseudogram varchar(400) NULL,
	TitleSubtitle varchar(400) NULL,
	LastUpdate timestamp NULL,
	FOREIGN KEY (PerformersID) REFERENCES Performers(PerformersID)
)  ENGINE=InnoDB;

CREATE TABLE Works(
	WorksID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	WorkName varchar(50) NULL,
	DateofCopyright datetime NULL,
	GenresID int NULL,
	LyricAdaptation varchar(1) NULL DEFAULT 'O',
	MusicArrangement varchar(1) NULL DEFAULT 'O',
	WorksID_Original BIGINT NULL,
	ISWC varchar(20) NULL,
	LanguagesID int NULL,
	Duration datetime NULL,
	PerformersID BIGINT NULL,
	WorksID_Replaced int NULL,
	CountriesID BIGINT NULL,
	FOREIGN KEY (CountriesID) REFERENCES Countries(CountriesID),
	FOREIGN KEY (PerformersID) REFERENCES Performers(PerformersID),
	FOREIGN KEY (WorksID_Original) REFERENCES Works(WorksID)
)  ENGINE=InnoDB;


CREATE TABLE ISRC(
	ISRCID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ISRC varchar(12) NOT NULL,
	SongTitle varchar(100) NOT NULL,
	SongOwners varchar(100) NULL,
	Performer varchar(100) NULL,
	CountryCode varchar(2) NULL,
	CompanyCode varchar(5) NULL,
	SourcesExternalCodesID int NULL,
	Year varchar(2) NULL,
	Number varchar(5) NULL,
	PerformersID BIGINT NULL,
	NLS_ToBeChecked_Again char(1) NULL DEFAULT 'Y',
	RecordingTypesID int NULL,
	GenresID int NULL,
	ISWC varchar(20) NULL,
	Duration datetime NULL,
	CustomersID BIGINT NULL,
	GRID varchar(20) NULL,
	FOREIGN KEY (CustomersID) REFERENCES Customers(CustomersID),
	FOREIGN KEY (PerformersID) REFERENCES Performers(PerformersID)
) ENGINE=InnoDB;

CREATE TABLE CollectionDetailsDistinct(
	CollectionDetailsDistinctID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	SourcesID BIGINT NOT NULL,
	SongCode varchar(100) NULL,
	SongTitle varchar(100) NULL,
	SongOwners varchar(100) NULL,
	ReleaseCode varchar(100) NULL,
	ReleasePerformer varchar(100) NULL,
	WorksID BIGINT NULL,
	NOPRIncomingSongsHeadsID int NULL,
	UsageUnlinkedWorksID BIGINT NULL,
	UsageToProcess varchar(1) NULL DEFAULT 'N',
	ISRC varchar(12) NULL DEFAULT '',
	DoNotUseThisGroup varchar(1) NOT NULL DEFAULT 'N',
	GRID varchar(20) NULL,
	ISWC varchar(20) NULL,
	ISRCId BIGINT NULL,
	FOREIGN KEY (ISRCId) REFERENCES ISRC(ISRCID),
	FOREIGN KEY (SourcesID) REFERENCES Sources(SourcesID),
	FOREIGN KEY (UsageUnlinkedWorksID) REFERENCES UsageUnlinkedWorks(UsageUnlinkedWorksID),
	FOREIGN KEY (WorksID) REFERENCES Works(WorksID)
) ENGINE=InnoDB;

CREATE TABLE Companies(
	CompaniesID BIGINT NOT NULL PRIMARY KEY,
	CompanyName varchar(50) NULL,
	CurrenciesID int NULL,
	Emi varchar(1) NULL,
	Online varchar(1) NULL,
	Representative varchar(1) NULL,
	FOREIGN KEY (CompaniesID) REFERENCES Sources(SourcesID)
) ENGINE=InnoDB;

CREATE TABLE CollectionHeaders(
	CollectionHeadersID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	CollectionType varchar(1) NULL,
	SourcesID BIGINT NULL,
	Description varchar(100) NULL,
	CurrenciesID int NULL,
	StartDate datetime NULL,
	EndDate datetime NULL,
	FileName varchar(50) NULL,
	BatchJobHeaderID int NULL,
	OriginalFileName varchar(50) NULL,
	FormatsID int NULL,
	TotalAmmountReceived numeric(18, 2) NULL,
	AllFieldsLinked varchar(1) NULL,
	AllReleasesLinked varchar(1) NULL,
	Closed varchar(1) NULL,
	CompaniesID BIGINT NULL,
	Claim0Collect varchar(1) NULL DEFAULT 'N',
	TransactionTypeID int NULL,
	TransactionTypeID_Prevision int NULL,
	InvoiceID int NULL,
	CurrencyFactor numeric(7, 3) NULL,
	CollectionHeadersGroupsID int NULL,
	ReadyForRoy varchar(1) NOT NULL DEFAULT 'N',
	FOREIGN KEY (CompaniesID) REFERENCES Companies(CompaniesID),
	FOREIGN KEY (SourcesID) REFERENCES Sources(SourcesID)
) ENGINE=InnoDB;

CREATE TABLE Rights(
	RightsID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	RightName varchar(30) NULL,
	RightsID_Parent BIGINT NULL,
	RightsCode varchar(50) NULL,
	RightUnit varchar(50) NULL,
	IncomeTypeCode varchar(3) NULL,
	FOREIGN KEY (RightsID_Parent) REFERENCES Rights(RightsID)
)  ENGINE=InnoDB;

CREATE TABLE CollectionDetails(
	CollectionDetailsID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	CollectionHeadersID BIGINT NULL,
	ImportedSource varchar(100) NULL,
	CountriesID_Ammounts BIGINT NULL,
	SongCode varchar(100) NULL,
	SongTitle varchar(100) NULL,
	SongOwners varchar(100) NULL,
	StartDate datetime NULL,
	EndDate datetime NULL,
	ImportedRight varchar(100) NULL,
	RightsID BIGINT NULL,
	PayerGrossIncome numeric(21, 9) NULL,
	CollectedShare numeric(18, 4) NULL,
	AmmountReceived numeric(21, 9) NULL,
	StaturyRate numeric(21, 9) NULL,
	CatalogCode varchar(100) NULL,
	ImportedCatalog varchar(100) NULL,
	Notes varchar(1000) NULL,
	ReleaseFormat varchar(100) NULL,
	FormatsID int NULL,
	ReleaseCode varchar(100) NULL,
	ReleaseHeaderTitle varchar(100) NULL,
	UsageReleasesID int NULL,
	ImportedCustomer varchar(100) NULL,
	CustomersID BIGINT NULL,
	ReleasePerformer varchar(100) NULL,
	PerformersID BIGINT NULL,
	ReleaseRSP numeric(10, 2) NULL,
	ReleasePPD numeric(10, 2) NULL,
	ReleaseCollectedRate numeric(10, 5) NULL,
	ReleaseTotalTracks numeric(10, 2) NULL,
	UnitsCollected numeric(10, 2) NULL,
	WRPresentationDate datetime NULL,
	OriginalString varchar(2000) NULL,
	AllFieldsLinked varchar(1) NULL,
	ReleaseLinked varchar(1) NULL,
	Claim varchar(1) NOT NULL,
	AmmountsCountry varchar(10) NULL,
	CollectionDetailsGroupID int NULL,
	CollectionDetailsGroupID_Transfer int NULL,
	NoTicketGeneration varchar(1) NULL,
	CollectionDetailsGroupID_Parent int NULL,
	CollectDateRef_Original datetime NULL,
	CollectionDetailsID_Original varchar(100) NULL,
	ClaimSolved char(1) NULL,
	ClaimTransferShare numeric(7, 2) NULL,
	ISWC varchar(20) NULL,
	ImportedWorksID int NULL,
	CollectionDetailsDistinctID int NULL,
	ISRC varchar(20) NULL,
	GRID varchar(20) NULL,
	External_Publisers varchar(100) NULL,
	SpecialProjectID int NULL,
	SongRSP numeric(10, 4) NULL,
	FOREIGN KEY (CollectionHeadersID) REFERENCES CollectionHeaders(CollectionHeadersID),
	FOREIGN KEY (CountriesID_Ammounts) REFERENCES Countries(CountriesID),
	FOREIGN KEY (CustomersID) REFERENCES Customers(CustomersID),
	FOREIGN KEY (PerformersID) REFERENCES Performers(PerformersID),
	FOREIGN KEY (RightsID) REFERENCES Rights(RightsID)
) ENGINE=InnoDB;


CREATE TABLE CopyRight(
	CopyRightID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	WorksID BIGINT NULL,
	OwnersID BIGINT NULL,
	RightsID BIGINT NULL,
	RolesID int NULL,
	CopyRightShare numeric(7, 3) NULL,
	deleted varchar(1) NULL DEFAULT 'N',
	FOREIGN KEY (OwnersID) REFERENCES Owners(OwnersID),
	FOREIGN KEY (RightsID) REFERENCES Rights(RightsID),
	FOREIGN KEY (WorksID) REFERENCES Works(WorksID)
) ENGINE=InnoDB;


CREATE TABLE Publishers(
	PublishersID BIGINT NOT NULL PRIMARY KEY,
	PublishersID_Parent BIGINT NULL,
	PublisherName varchar(50) NULL,
	Emi varchar(1) NULL,
	Online varchar(1) NULL DEFAULT 'N',
	Virtual varchar(1) NULL DEFAULT 'N',
	PublishersCode varchar(50) NULL,
	CompaniesID BIGINT NULL,
	FOREIGN KEY (CompaniesID) REFERENCES Companies(CompaniesID),
	FOREIGN KEY (PublishersID) REFERENCES Owners(OwnersID),
	FOREIGN KEY (PublishersID_Parent) REFERENCES Publishers(PublishersID)
) ENGINE=InnoDB;

CREATE TABLE DocumentHeader(
	DocumentHeaderID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	DealsID int NULL,
	PublishersID_Acquirer BIGINT NOT NULL,
	DocumentType varchar(3) NOT NULL,
	DocumentFull varchar(1) NULL,
	DocumentGeneral varchar(1) NULL,
	IncomingNoprNumber varchar(50) NULL,
	TerritoriesID int NULL,
	StartDocument datetime NULL,
	StartCollection datetime NULL,
	DocumentPrior varchar(1) NULL DEFAULT 'N',
	EndDocument datetime NULL,
	EndRetention datetime NULL,
	EndCollection datetime NULL,
	Note text NULL,
	AtSource varchar(1) NULL DEFAULT 'N',
	IntercompanyRates varchar(1) NULL DEFAULT 'Y',
	SeparateAccounting varchar(1) NULL DEFAULT 'N',
	LockDocuments varchar(1) NULL DEFAULT 'N',
	LockAssignors varchar(1) NULL DEFAULT 'N',
	LockAssignments varchar(1) NULL DEFAULT 'N',
	PublishersID_Ownership BIGINT NULL,
	AllowSpecific varchar(1) NULL DEFAULT 'N',
	EndDocumentText varchar(100) NULL,
	EndRetentionText varchar(100) NULL,
	EndCollectionText varchar(100) NULL,
	CollectByNPS varchar(1) NULL DEFAULT 'N',
	EXTERNAL_Deal_name varchar(60) NULL,
	Ignore_Assig_Roy_For_Royalties bit NULL DEFAULT 0,
	UsersID_Lock int NULL,
	S2W_Default int NULL DEFAULT 0,
	FOREIGN KEY (PublishersID_Acquirer) REFERENCES Publishers(PublishersID),
	FOREIGN KEY (PublishersID_Ownership) REFERENCES Publishers(PublishersID)
) ENGINE=InnoDB;

CREATE TABLE DocumentAssignor(
	DocumentAssignorID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	DocumentHeaderID BIGINT NULL,
	OwnersID BIGINT NOT NULL,
	FOREIGN KEY (DocumentHeaderID) REFERENCES DocumentHeader(DocumentHeaderID),
	FOREIGN KEY (OwnersID) REFERENCES Owners(OwnersID)
) ENGINE=InnoDB;

CREATE TABLE Documents(
	DocumentsID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	DocumentHeaderID BIGINT NULL,
	DocumentNumber int NULL,
	DocumentDate datetime NULL,
	LarsFile varchar(15) NULL,
	OwnAcquiredShare numeric(7, 3) NULL,
	AdminAcquiredShare numeric(7, 3) NULL,
	AssignorsAssignmentCode varchar(50) NULL,
	WorksID_Acquired BIGINT NULL,
	RightsID_Acquired BIGINT NULL,
	DocumentHeaderID_Acquired BIGINT NULL,
	DocumentsID_Acquired BIGINT NULL,
	Notes text NULL,
	Controlled char(1) NULL,
	DocumentHash varchar(20) NULL,
	NOPRIncomingSongsHeadsID int NULL,
	FOREIGN KEY (DocumentHeaderID) REFERENCES DocumentHeader(DocumentHeaderID),
	FOREIGN KEY (DocumentHeaderID_Acquired) REFERENCES DocumentHeader(DocumentHeaderID),
	FOREIGN KEY (DocumentsID_Acquired) REFERENCES Documents(DocumentsID),
	FOREIGN KEY (RightsID_Acquired) REFERENCES Rights(RightsID),
	FOREIGN KEY (WorksID_Acquired) REFERENCES Works(WorksID)
) ENGINE=InnoDB;

CREATE TABLE DocumentsCopyrights(
	DocumentsCopyrightsID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	DocumentsID BIGINT NULL,
	CopyrightID BIGINT NULL,
	FOREIGN KEY (CopyrightID) REFERENCES CopyRight(CopyRightID),
	FOREIGN KEY (DocumentsID) REFERENCES Documents(DocumentsID)
) ENGINE=InnoDB;

CREATE TABLE DocumentsTypes(
	DocumentsTypesID varchar(3) NOT NULL PRIMARY KEY,
	DocumentTypeCode varchar(5) NULL,
	DocumentTypeName varchar(50) NULL
)  ENGINE=InnoDB;

CREATE TABLE ISRC_Details(
	ISRC_DetailsID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ISRC_HeaderID int NOT NULL,
	ISRCID BIGINT NULL,
	SongCode varchar(100) NULL,
	ReleaseCode varchar(100) NULL,
	ReleaseHeaderTitle varchar(100) NULL,
	TotalUnits numeric(10, 2) NULL,
	ReleasePPD numeric(10, 2) NULL,
	ReleaseRSP numeric(10, 2) NULL,
	FonogramCode varchar(100) NULL,
	Position int NULL,
	Duration datetime NULL,
	FOREIGN KEY (ISRCID) REFERENCES ISRC(ISRCID)
) ENGINE=InnoDB;

CREATE TABLE UsageTypes(
	UsageTypesID BIGINT NOT NULL PRIMARY KEY,
	UsageTypeName varchar(50) NULL,
	RightsID BIGINT NULL,
	AcceptsLicenses char(1) NULL,
	FOREIGN KEY (RightsID) REFERENCES Rights(RightsID)
) ENGINE=InnoDB;

CREATE TABLE UsageWork(
	UsageWorkID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	WorksID BIGINT NULL,
	NOPRIncomingSongsHeadsID int NULL,
	UsageUnlinkedWorksID BIGINT NULL,
	UsageTypesID BIGINT NULL,
	LastUpdate timestamp NULL,
	FOREIGN KEY (UsageTypesID) REFERENCES UsageTypes(UsageTypesID),
	FOREIGN KEY (UsageUnlinkedWorksID) REFERENCES UsageUnlinkedWorks(UsageUnlinkedWorksID),
	FOREIGN KEY (WorksID) REFERENCES Works(WorksID)
) ENGINE=InnoDB;

CREATE TABLE ISRC_Songs(
	ISRC_SongsID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ISRCID BIGINT NOT NULL,
	Share numeric(7, 3) NULL,
	UsageWorkID BIGINT NOT NULL,
	Duration datetime NULL,
	ImportedSongTitle varchar(100) NULL,
	ImportedSongOwners varchar(500) NULL,
	ImportedSongPerformer varchar(100) NULL,
	FOREIGN KEY (ISRCID) REFERENCES ISRC(ISRCID),
	FOREIGN KEY (UsageWorkID) REFERENCES UsageWork(UsageWorkID)
) ENGINE=InnoDB;


CREATE TABLE ISRC_SongsExternalCodes(
	ISRC_SongsExternalCodesID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	SourcesID BIGINT NOT NULL,
	ExternalCode varchar(20) NOT NULL,
	ISRC_SongsID BIGINT NOT NULL,
	FOREIGN KEY (ISRC_SongsID) REFERENCES ISRC_Songs(ISRC_SongsID),
	FOREIGN KEY (SourcesID) REFERENCES Sources(SourcesID)
) ENGINE=InnoDB;


CREATE TABLE OwnersAKAInformation(
	OwnersAKAInformationID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	OwnersID BIGINT NOT NULL,
	Pseudonym varchar(100) NULL,
	NameType varchar(1) NULL,
	Pseudogram varchar(400) NULL,
	LastUpdate timestamp NULL,
	CAE_IPI_Number varchar(20) NULL,
	FOREIGN KEY (OwnersID) REFERENCES Owners(OwnersID)
) ENGINE=InnoDB;

CREATE TABLE OwnersExternalCodes(
	OwnersExternalCodesID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	OwnersID BIGINT NULL,
	OwnerName varchar(102) NOT NULL,
	SourcesID_Informer BIGINT NOT NULL,
	ExternalCode varchar(20) NOT NULL,
	UsersID int NULL,
	FOREIGN KEY (OwnersID) REFERENCES Owners(OwnersID),
	FOREIGN KEY (SourcesID_Informer) REFERENCES Sources(SourcesID)
) ENGINE=InnoDB;


CREATE TABLE OwnersPersonalInfo(
	OwnersPersonalInfoID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	OwnersID BIGINT NULL,
	StreetAddress varchar(100) NULL,
	City varchar(50) NULL,
	State varchar(50) NULL,
	ZipCode varchar(20) NULL,
	CountriesID BIGINT NULL,
	Telephone varchar(50) NULL,
	Fax varchar(50) NULL,
	Email varchar(50) NULL,
	DocumentType varchar(5) NULL,
	DocumentNumber varchar(30) NULL,
	TaxDocumentType varchar(5) NULL,
	TaxDocumentNumber varchar(30) NULL,
	Deleted datetime NULL,
	Web varchar(100) NULL,
	FOREIGN KEY (CountriesID) REFERENCES Countries(CountriesID),
	FOREIGN KEY (OwnersID) REFERENCES Owners(OwnersID)
) ENGINE=InnoDB;

CREATE TABLE OwnersSocieties(
	OwnersSocietiesID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	CAE_IPI_Number varchar(20) NULL,
	AuthorSocietiesID BIGINT NULL,
	OwnersID BIGINT NULL,
	IPI_Base_Number varchar(20) NULL,
	AffiliationDate datetime NULL,
	DeAffiliationDate datetime NULL,
	AffiliationNumber varchar(20) NULL,
	RightsID BIGINT NULL,
	TerritoriesID int NULL,
	FOREIGN KEY (RightsID) REFERENCES Rights(RightsID),
	FOREIGN KEY (AuthorSocietiesID) REFERENCES AuthorSocieties(AuthorSocietiesID),
	FOREIGN KEY (OwnersID) REFERENCES Owners(OwnersID)
) ENGINE=InnoDB;

CREATE TABLE Usages(
	UsagesID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	Origin varchar(50) NULL,
	UsageDetailsLCID int NULL,
	UsageHeaderLCReleaseID int NULL,
	DocumentsID BIGINT NULL,
	LicensesID int NULL,
	FOREIGN KEY (DocumentsID) REFERENCES Documents(DocumentsID)
) ENGINE=InnoDB;


CREATE TABLE WorksAKAInformation(
	WorksAKAInformationID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	WorksID BIGINT NULL,
	Pseudonym varchar(100) NULL,
	TytleType varchar(1) NULL,
	Pseudogram varchar(400) NULL,
	LastUpdate timestamp NULL,
	FOREIGN KEY (WorksID) REFERENCES Works(WorksID)
)  ENGINE=InnoDB;

CREATE TABLE WorksExternalCodes(
	WorksExternalCodesID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	WorksID BIGINT NOT NULL,
	WorkName varchar(102) NULL,
	SourcesID_Informer BIGINT NOT NULL,
	ExternalCode varchar(20) NULL,
	UsersID int NULL,
	FOREIGN KEY (SourcesID_Informer) REFERENCES Sources(SourcesID),
	FOREIGN KEY (WorksID) REFERENCES Works(WorksID)
) ENGINE=InnoDB;

CREATE TABLE Works_IntendedPurpose(
	Works_IntendedPurposeID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	WorksID BIGINT NOT NULL,
	IntendedPurposeID int NOT NULL,
	UsageDescription varchar(100) NULL,
	UsageNotes varchar(1000) NULL,
	Creation_Date datetime NULL,
	FOREIGN KEY (WorksID) REFERENCES Works(WorksID)
) ENGINE=InnoDB;

CREATE TABLE Writers(
	WritersID BIGINT NOT NULL PRIMARY KEY,
	LastName varchar(50) NULL,
	FirstName varchar(50) NULL,
	DateofBirth datetime NULL,
	DateofDeath datetime NULL,
	PlaceofBirth varchar(50) NULL,
	State varchar(1) NULL,
	FOREIGN KEY (WritersID) REFERENCES Owners(OwnersID)
)  ENGINE=InnoDB;

