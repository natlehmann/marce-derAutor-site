IF EXISTS(select * FROM sys.views where name = 'VIEW_MoneyAmounts') 
	DROP VIEW VIEW_MoneyAmounts
GO

CREATE VIEW VIEW_MoneyAmounts AS

	SELECT     	ROW_NUMBER() OVER(ORDER BY Works.WorksID) AS id,
				Receipt.CompaniesID AS companyId,
				Countries.CountriesID AS idPais,
				Countries.CountryName AS nombrePais, 
				DATEPART(yyyy,Invoice.InvoiceDate) AS anio,
	            CASE  
				  WHEN DATEPART(mm,Invoice.InvoiceDate) BETWEEN 1 AND 3 THEN 1 
				  WHEN DATEPART(mm,Invoice.InvoiceDate) BETWEEN 4 AND 6 THEN 2
				  WHEN DATEPART(mm,Invoice.InvoiceDate) BETWEEN 7 AND 9 THEN 3 
				  ELSE 4 
			  	END as trimestre,
				Works.WorksID AS idCancion, 
				Works.WorkName AS nombreCancion, 
				CopyRight.OwnersID AS idAutor, 
	            Owners_All.UniqueName AS nombreAutor, 
	            Sources_All.SourcesID AS idFuente,
	            Sources_All.UniqueName AS nombreFuente, 
	            Rights.RightName AS rightName, 
	            Receipt.CurrencyFactor * SUM(CollectionDetails.AmmountReceived) * (CopyRight.CopyRightShare/100) AS montoPercibido
	
	FROM      CollectionDetails INNER JOIN
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
	          Countries ON CollectionDetails.CountriesID_Ammounts = Countries.CountriesID
	                      
	GROUP BY 	  Countries.CountriesID,
	              Countries.CountryName,
	              Works.WorksID,
				  Works.WorkName, 
				  CopyRight.OwnersID, 
				  Owners_All.UniqueName, 
				  CopyRight.CopyRightShare, 
				  OwnersSocieties.AuthorSocietiesID, 
				  Sources_All.SourcesID,
	              Sources_All.UniqueName, 
	              Rights.RightName, 
	              Receipt.CurrencyFactor, 
	              Invoice.InvoiceDate, 
	              Receipt.CompaniesID
	                      
	HAVING      (OwnersSocieties.AuthorSocietiesID = 59)
GO
	

IF EXISTS(select * FROM sys.views where name = 'VIEW_Units') 
	DROP VIEW VIEW_Units
GO
	
CREATE VIEW VIEW_Units AS

	SELECT     	ROW_NUMBER() OVER(ORDER BY Works.WorksID) AS id,
				Countries.CountriesID AS idPais,
				Countries.CountryName AS nombrePais, 
				DATEPART(yyyy,RNKHeaders.EndDate) AS anio,
	            CASE  
				  WHEN DATEPART(mm,RNKHeaders.EndDate) BETWEEN 1 AND 3 THEN 1 
				  WHEN DATEPART(mm,RNKHeaders.EndDate) BETWEEN 4 AND 6 THEN 2
				  WHEN DATEPART(mm,RNKHeaders.EndDate) BETWEEN 7 AND 9 THEN 3 
				  ELSE 4 
			  	END as trimestre,
				Works.WorksID AS idCancion, 
				Works.WorkName AS nombreCancion, 
				CopyRight.OwnersID AS idAutor, 
	            Owners_All.UniqueName AS nombreAutor, 
	            Rights.RightName AS rightName, 
	            ROUND( SUM(RNKDetails.Units) * CurrenciesConvertion.Factor * (CopyRight.CopyRightShare/100), 0)  AS cantidadUnidades

	FROM      CurrenciesConvertion INNER JOIN
              Currencies ON CurrenciesConvertion.CurrenciesID_From = Currencies.CurrenciesID INNER JOIN
              RNKDetails INNER JOIN
              Works INNER JOIN
              UsageWork ON Works.WorksID = UsageWork.WorksID INNER JOIN
              CopyRight ON Works.WorksID = CopyRight.WorksID INNER JOIN
              Owners ON CopyRight.OwnersID = Owners.OwnersID INNER JOIN
              OwnersSocieties ON Owners.OwnersID = OwnersSocieties.OwnersID INNER JOIN
              Owners_All ON Owners.OwnersID = Owners_All.OwnersID 
              ON RNKDetails.UsageWorkID = UsageWork.UsageWorkID INNER JOIN
              RNKHeaders ON RNKDetails.RNKHeadersID = RNKHeaders.RNKHeadersID INNER JOIN
              Countries INNER JOIN
              Sources ON Countries.CountriesID = Sources.CountriesID ON RNKHeaders.SourcesID = Sources.SourcesID ON 
              Currencies.CountriesID = Sources.CountriesID INNER JOIN
              Rights ON RNKHeaders.RightID = Rights.RightsID
                      
	GROUP BY 	  Countries.CountriesID,
	              Countries.CountryName,
	              Works.WorksID,
				  Works.WorkName, 
				  CopyRight.OwnersID, 
				  Owners_All.UniqueName, 
				  CopyRight.CopyRightShare, 
				  OwnersSocieties.AuthorSocietiesID,  
	              Rights.RightName,
	              Sources.SourcesID_Parent,
	              RNKHeaders.EndDate,
	              CurrenciesConvertion.Factor
                      
	HAVING      (OwnersSocieties.AuthorSocietiesID = 59) AND (Sources.SourcesID_Parent = 9)
GO



IF EXISTS(select * FROM sys.views where name = 'VIEW_AuthorsIds') 
	DROP VIEW VIEW_AuthorsIds
GO

CREATE VIEW VIEW_AuthorsIds AS

	SELECT 	  CopyRight.OwnersID 
	FROM	  CollectionDetails INNER JOIN
	          CollectionHeaders ON CollectionDetails.CollectionHeadersID = CollectionHeaders.CollectionHeadersID INNER JOIN
	          CollectionDetailsDistinct ON CollectionDetails.CollectionDetailsDistinctID = CollectionDetailsDistinct.CollectionDetailsDistinctID INNER JOIN
	          Works ON CollectionDetailsDistinct.WorksID = Works.WorksID INNER JOIN
	          CopyRight ON Works.WorksID = CopyRight.WorksID
	UNION
	
	SELECT 	  CopyRight.OwnersID
	FROM 	  RNKDetails INNER JOIN
              Works INNER JOIN
              UsageWork ON Works.WorksID = UsageWork.WorksID INNER JOIN
              CopyRight ON Works.WorksID = CopyRight.WorksID
              ON RNKDetails.UsageWorkID = UsageWork.UsageWorkID
GO