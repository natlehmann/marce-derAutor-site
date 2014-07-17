CREATE PROCEDURE sp_amountsRanking
    /* Input Parameters */
	@companyId int,
    @idPais int,
    @anio int,
    @trimestre int,
    @filtro varchar(100),
    @inicioPaginacion int,
    @finPaginacion int
        
AS
    Set NoCount ON
    /* Variable Declaration */
    Declare @SQLQuery AS NVarchar(4000)
    Declare @ParamDefinition AS NVarchar(2000) 
    
    Declare @inicio as int
    Declare @fin as int
    Declare @filtroSql varchar(102)
    
    If @filtro Is Not Null
    	Set @filtroSql = '%' + @filtro + '%'
    
    /* Build the Transact-SQL String with the input parameters */ 
    Set @SQLQuery = 'SELECT ranking AS id,ranking,idPais,anio,trimestre,idAutor,nombreAutor,montoPercibido,cantidadUnidades
					 FROM (SELECT ROW_NUMBER() OVER(ORDER BY montoPercibido desc) AS ranking, ' 
    				+ 'idPais,anio,trimestre,idAutor,nombreAutor,montoPercibido,cantidadUnidades '
    				+ 'FROM ( SELECT '
    				
    If @idPais Is Not Null 
         Set @SQLQuery = @SQLQuery + '@idPais'
    else
    	Set @SQLQuery = @SQLQuery + 'null'
    	
    Set @SQLQuery = @SQLQuery + ' AS idPais, '
    
    
    If @anio Is Not Null 
         Set @SQLQuery = @SQLQuery + '@anio'
    else
    	Set @SQLQuery = @SQLQuery + 'null'
    	
    Set @SQLQuery = @SQLQuery + ' AS anio, '
    
    
    If @trimestre Is Not Null 
         Set @SQLQuery = @SQLQuery + '@trimestre'
    else
    	Set @SQLQuery = @SQLQuery + 'null'
    	
    Set @SQLQuery = @SQLQuery + ' AS trimestre, '
    
    
    Set @SQLQuery = @SQLQuery + 'CopyRight.OwnersID AS idAutor, Owners_All.UniqueName AS nombreAutor, 0 AS cantidadUnidades, '
    				+ 'SUM(Receipt.CurrencyFactor * CollectionDetails.AmmountReceived * (CopyRight.CopyRightShare/100)) AS montoPercibido '
    				+ 'FROM      CollectionDetails INNER JOIN
	          CollectionHeaders ON CollectionDetails.CollectionHeadersID = CollectionHeaders.CollectionHeadersID INNER JOIN
	          CollectionDetailsDistinct ON CollectionDetails.CollectionDetailsDistinctID = CollectionDetailsDistinct.CollectionDetailsDistinctID INNER JOIN
	          Works ON CollectionDetailsDistinct.WorksID = Works.WorksID INNER JOIN
	          CopyRight ON Works.WorksID = CopyRight.WorksID INNER JOIN
	          Owners ON CopyRight.OwnersID = Owners.OwnersID INNER JOIN
	          OwnersSocieties ON Owners.OwnersID = OwnersSocieties.OwnersID INNER JOIN
	          Owners_All ON Owners.OwnersID = Owners_All.OwnersID INNER JOIN
	          Invoice ON CollectionHeaders.InvoiceID = Invoice.InvoiceID INNER JOIN
	          ReceiptInvoice ON Invoice.InvoiceID = ReceiptInvoice.InvoiceID INNER JOIN
	          Receipt ON ReceiptInvoice.ReceiptID = Receipt.ReceiptID INNER JOIN
	          Countries ON CollectionDetails.CountriesID_Ammounts = Countries.CountriesID
	                      
	GROUP BY 	  CopyRight.OwnersID, 
				  Owners_All.UniqueName, 
				  OwnersSocieties.AuthorSocietiesID, 
	              Receipt.CompaniesID'
    				
   If @idPais Is Not Null 
         Set @SQLQuery = @SQLQuery + ',Countries.CountriesID'
         
   If @anio Is Not Null or @trimestre Is Not Null
         Set @SQLQuery = @SQLQuery + ',Invoice.InvoiceDate'
         
   
   Set @SQLQuery = @SQLQuery + ' HAVING OwnersSocieties.AuthorSocietiesID = 59 AND Receipt.CompaniesID=@companyId '

   If @idPais Is Not Null 
         Set @SQLQuery = @SQLQuery + 'AND Countries.CountriesID = @idPais '
         
   If @anio Is Not Null 
         Set @SQLQuery = @SQLQuery + 'AND DATEPART(yyyy,Invoice.InvoiceDate) = @anio '
         
   If @trimestre Is Not Null 
   BEGIN
   		 Set @inicio = (@trimestre - 1) * 3 + 1
   		 Set @fin = @inicio + 2
         Set @SQLQuery = @SQLQuery + 'AND DATEPART(mm,Invoice.InvoiceDate) BETWEEN @inicio AND @fin '
   END
    
	If @filtroSql Is Not Null 
         Set @SQLQuery = @SQLQuery + 'AND Owners_All.UniqueName LIKE @filtroSql '
         
    Set @SQLQuery = @SQLQuery + ') as tmp) AS tmp2 
		WHERE ranking BETWEEN @inicioPaginacion AND @finPaginacion order by ranking'
    
    
    /* Specify Parameter Format for all input parameters included 
     in the stmt */
    Set @ParamDefinition =  '@companyId int,
						    @idPais int,
						    @anio int,
						    @trimestre int,
						    @filtroSql varchar(102),
							@inicio int,
							@fin int,
							@inicioPaginacion int,
    						@finPaginacion int'
    /* Execute the Transact-SQL String with all parameter value's 
       Using sp_executesql Command */
    Execute sp_Executesql     
    			@SQLQuery, 
                @ParamDefinition, 
                @companyId, 
                @idPais,
                @anio, 
                @trimestre, 
                @filtroSql, 
                @inicio,
                @fin,
                @inicioPaginacion,
                @finPaginacion
                
    If @@ERROR <> 0 GoTo ErrorHandler
    Set NoCount OFF
    Return(0)
  
ErrorHandler:
    Return(@@ERROR)
GO



CREATE PROCEDURE sp_amountsRankingCount
    /* Input Parameters */
	@companyId int,
    @idPais int,
    @anio int,
    @trimestre int,
    @filtro varchar(100)
        
AS
    Set NoCount ON
    /* Variable Declaration */
    Declare @SQLQuery AS NVarchar(4000)
    Declare @ParamDefinition AS NVarchar(2000) 
    
    Declare @inicio as int
    Declare @fin as int
    Declare @filtroSql varchar(102)
    
    If @filtro Is Not Null
    	Set @filtroSql = '%' + @filtro + '%'
    
    /* Build the Transact-SQL String with the input parameters */ 
    Set @SQLQuery = 'SELECT COUNT (DISTINCT CopyRight.OwnersID) as cantidad '
    				+ 'FROM      CollectionDetails INNER JOIN
	          CollectionHeaders ON CollectionDetails.CollectionHeadersID = CollectionHeaders.CollectionHeadersID INNER JOIN
	          CollectionDetailsDistinct ON CollectionDetails.CollectionDetailsDistinctID = CollectionDetailsDistinct.CollectionDetailsDistinctID INNER JOIN
	          Works ON CollectionDetailsDistinct.WorksID = Works.WorksID INNER JOIN
	          CopyRight ON Works.WorksID = CopyRight.WorksID INNER JOIN
	          Owners ON CopyRight.OwnersID = Owners.OwnersID INNER JOIN
	          OwnersSocieties ON Owners.OwnersID = OwnersSocieties.OwnersID INNER JOIN
	          Owners_All ON Owners.OwnersID = Owners_All.OwnersID INNER JOIN
	          Invoice ON CollectionHeaders.InvoiceID = Invoice.InvoiceID INNER JOIN
	          ReceiptInvoice ON Invoice.InvoiceID = ReceiptInvoice.InvoiceID INNER JOIN
	          Receipt ON ReceiptInvoice.ReceiptID = Receipt.ReceiptID INNER JOIN
	          Countries ON CollectionDetails.CountriesID_Ammounts = Countries.CountriesID '
    				
   Set @SQLQuery = @SQLQuery + ' WHERE OwnersSocieties.AuthorSocietiesID = 59 AND Receipt.CompaniesID=@companyId '

   If @idPais Is Not Null 
         Set @SQLQuery = @SQLQuery + 'AND Countries.CountriesID = @idPais '
         
   If @anio Is Not Null 
         Set @SQLQuery = @SQLQuery + 'AND DATEPART(yyyy,Invoice.InvoiceDate) = @anio '
         
   If @trimestre Is Not Null 
   BEGIN
   		 Set @inicio = (@trimestre - 1) * 3 + 1
   		 Set @fin = @inicio + 2
         Set @SQLQuery = @SQLQuery + 'AND DATEPART(mm,Invoice.InvoiceDate) BETWEEN @inicio AND @fin '
   END
    
	If @filtroSql Is Not Null 
         Set @SQLQuery = @SQLQuery + 'AND Owners_All.UniqueName LIKE @filtroSql '
         
    
    /* Specify Parameter Format for all input parameters included 
     in the stmt */
    Set @ParamDefinition =  '@companyId int,
						    @idPais int,
						    @anio int,
						    @trimestre int,
						    @filtroSql varchar(102),
							@inicio int,
							@fin int'
    /* Execute the Transact-SQL String with all parameter value's 
       Using sp_executesql Command */
    Execute sp_Executesql     
    			@SQLQuery, 
                @ParamDefinition, 
                @companyId, 
                @idPais,
                @anio, 
                @trimestre, 
                @filtroSql, 
                @inicio,
                @fin
                
    If @@ERROR <> 0 GoTo ErrorHandler
    Set NoCount OFF
    Return(0)
  
ErrorHandler:
    Return(@@ERROR)
GO








SELECT     	ROW_NUMBER() OVER(ORDER BY montoPercibido desc) AS ranking,
			idPais,anio,trimestre,idAutor,nombreAutor, montoPercibido, cantidadUnidades
FROM (

SELECT
				7 AS idPais,
				2013 AS anio,
	            2 as trimestre,
				CopyRight.OwnersID AS idAutor, 
	            Owners_All.UniqueName AS nombreAutor, 
	            SUM(Receipt.CurrencyFactor * CollectionDetails.AmmountReceived * (CopyRight.CopyRightShare/100)) AS montoPercibido
	
	FROM      CollectionDetails INNER JOIN
	          CollectionHeaders ON CollectionDetails.CollectionHeadersID = CollectionHeaders.CollectionHeadersID INNER JOIN
	          CollectionDetailsDistinct ON CollectionDetails.CollectionDetailsDistinctID = CollectionDetailsDistinct.CollectionDetailsDistinctID INNER JOIN
	          Works ON CollectionDetailsDistinct.WorksID = Works.WorksID INNER JOIN
	          CopyRight ON Works.WorksID = CopyRight.WorksID INNER JOIN
	          Owners ON CopyRight.OwnersID = Owners.OwnersID INNER JOIN
	          OwnersSocieties ON Owners.OwnersID = OwnersSocieties.OwnersID INNER JOIN
	          Owners_All ON Owners.OwnersID = Owners_All.OwnersID INNER JOIN
	          Invoice ON CollectionHeaders.InvoiceID = Invoice.InvoiceID INNER JOIN
	          ReceiptInvoice ON Invoice.InvoiceID = ReceiptInvoice.InvoiceID INNER JOIN
	          Receipt ON ReceiptInvoice.ReceiptID = Receipt.ReceiptID INNER JOIN
	          Countries ON CollectionDetails.CountriesID_Ammounts = Countries.CountriesID
	                      
	GROUP BY 	  CopyRight.OwnersID, 
				  Owners_All.UniqueName, 
				  OwnersSocieties.AuthorSocietiesID, 
	              Receipt.CompaniesID,
	              
	              Countries.CountriesID,
	              Invoice.InvoiceDate
	                      
	HAVING      (OwnersSocieties.AuthorSocietiesID = 59) AND Receipt.CompaniesID=14778 
				AND Countries.CountriesID=7
				AND DATEPART(yyyy,Invoice.InvoiceDate) = 2013
				AND DATEPART(mm,Invoice.InvoiceDate) BETWEEN 4 AND 6
				AND Owners_All.UniqueName LIKE '%AGUI%'
				
	) as tmp order by ranking