CREATE VIEW VIEW_UnitsAndAmounts AS

SELECT     	Receipt.CompaniesID AS companyId,
			Countries.CountriesID AS idPais,
			Countries.CountryName AS nombrePais, 
			Invoice.InvoiceDate AS fecha,
			57 AS formatId,
			Works.WorksID AS idCancion, 
			Works.WorkName AS nombreCancion, 
			CopyRight.OwnersID AS idAutor, 
            Owners_All.UniqueName AS nombreAutor, 
            Sources_All.SourcesID AS idFuente,
            Sources_All.UniqueName AS nombreFuente, 
            Rights.RightName AS rightName, 
            CopyRight.CopyRightShare AS copyRightShares,
            SUM(OniDetail.UnitsCollected) AS unidades,
            SourcesExternalCodes.ExternalCode AS externalCode,
            Receipt.CurrencyFactor AS currencyFactor, 
            SUM(CollectionDetails.AmmountReceived) AS localCurrency
            
FROM        CollectionDetails INNER JOIN
            CollectionHeaders ON CollectionDetails.CollectionHeadersID = CollectionHeaders.CollectionHeadersID INNER JOIN
            CollectionDetailsDistinct ON CollectionDetails.CollectionDetailsDistinctID = CollectionDetailsDistinct.CollectionDetailsDistinctID INNER JOIN
            Works ON CollectionDetailsDistinct.WorksID = Works.WorksID INNER JOIN
            CopyRight ON Works.WorksID = CopyRight.WorksID INNER JOIN
            Owners ON CopyRight.OwnersID = Owners.OwnersID INNER JOIN
            Rights ON CollectionDetails.RightsID = Rights.RightsID INNER JOIN
            OwnersSocieties ON Owners.OwnersID = OwnersSocieties.OwnersID INNER JOIN
            Owners_All ON Owners.OwnersID = Owners_All.OwnersID INNER JOIN
            Sources_All ON CollectionHeaders.SourcesID = Sources_All.SourcesID INNER JOIN
            Invoice ON CollectionHeaders.InvoiceID = Invoice.InvoiceID INNER JOIN
            ReceiptInvoice ON Invoice.InvoiceID = ReceiptInvoice.InvoiceID INNER JOIN
            Receipt ON ReceiptInvoice.ReceiptID = Receipt.ReceiptID INNER JOIN
            Countries ON CollectionDetails.CountriesID_Ammounts = Countries.CountriesID LEFT JOIN
            UsageWork ON UsageWork.WorksID = Works.WorksID LEFT JOIN
            OniDetailGroup ON OniDetailGroup.UsageWorkID = UsageWork.UsageWorkID LEFT JOIN
            OniDetail ON OniDetail.OniDetailGroupID = OniDetailGroup.OniDetailGroupID LEFT JOIN
			SourcesExternalCodes ON Sources_All.SourcesID = SourcesExternalCodes.SourcesID

GROUP BY 	Works.WorksID,
			Works.WorkName, 
			CopyRight.OwnersID, 
			CopyRight.CopyRightShare, 
			OwnersSocieties.AuthorSocietiesID, 
			Owners_All.UniqueName, 
            Sources_All.UniqueName, 
            Sources_All.SourcesID, 
            Rights.RightName, 
            Receipt.CurrencyFactor, 
            Invoice.InvoiceDate, 
            Receipt.CompaniesID, 
            Countries.CountryName,
            Countries.CountriesID,
            SourcesExternalCodes.ExternalCode

HAVING      (OwnersSocieties.AuthorSocietiesID = 59);