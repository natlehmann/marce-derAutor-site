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
         
   If @anio Is Not Null
         Set @SQLQuery = @SQLQuery + ',DATEPART(yyyy,Invoice.InvoiceDate)'
         
   If @trimestre Is Not Null
         Set @SQLQuery = @SQLQuery + ',DATEPART(mm,Invoice.InvoiceDate)'
         
   
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



CREATE PROCEDURE sp_amountsRankingForAuthors
    /* Input Parameters */
	@companyId int,
    @idPais int,
    @anio int,
    @trimestre int,
    @idsAutores varchar(2000)
    
AS
    Set NoCount ON
    /* Variable Declaration */
    Declare @SQLQuery AS NVarchar(4000)
    Declare @ParamDefinition AS NVarchar(2000) 
    
    Declare @inicio as int
    Declare @fin as int
    
    /* Build the Transact-SQL String with the input parameters */ 
    Set @SQLQuery = 'SELECT CopyRight.OwnersID AS idAutor, '
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
         
   If @anio Is Not Null
         Set @SQLQuery = @SQLQuery + ',DATEPART(yyyy,Invoice.InvoiceDate)'
         
   If @trimestre Is Not Null
         Set @SQLQuery = @SQLQuery + ',DATEPART(mm,Invoice.InvoiceDate)'
         
   
   Set @SQLQuery = @SQLQuery + ' HAVING OwnersSocieties.AuthorSocietiesID = 59 
								 AND Receipt.CompaniesID=@companyId 
								 AND CopyRight.OwnersID IN (' + @idsAutores + ') '

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
    
    /* Specify Parameter Format for all input parameters included 
     in the stmt */
    Set @ParamDefinition =  '@companyId int,
						    @idPais int,
						    @anio int,
						    @trimestre int,
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
                @inicio,
                @fin
                
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



CREATE PROCEDURE sp_amountsByWorkRanking
    /* Input Parameters */
	@companyId int,
    @idPais int,
    @anio int,
    @trimestre int,
    @idAutor int,
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
    Set @SQLQuery = 'SELECT ranking AS id,ranking,idPais,anio,trimestre,idAutor,nombreAutor,
					 idCancion,nombreCancion,montoPercibido,cantidadUnidades
					 FROM (SELECT '
    				
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
    
    
    Set @SQLQuery = @SQLQuery + 'ROW_NUMBER() OVER(ORDER BY Works.WorkName asc) AS ranking,
              		   CopyRight.OwnersID AS idAutor, Owners_All.UniqueName AS nombreAutor, 
					   Works.WorksID AS idCancion, Works.WorkName AS nombreCancion, 0 AS cantidadUnidades, '
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
	                      
	GROUP BY 	  Works.WorksID,
				  Works.WorkName,
				  CopyRight.OwnersID, 
				  Owners_All.UniqueName, 
				  OwnersSocieties.AuthorSocietiesID, 
	              Receipt.CompaniesID'
    				
   If @idPais Is Not Null 
         Set @SQLQuery = @SQLQuery + ',Countries.CountriesID'
         
   If @anio Is Not Null
         Set @SQLQuery = @SQLQuery + ',DATEPART(yyyy,Invoice.InvoiceDate)'
         
   If @trimestre Is Not Null
         Set @SQLQuery = @SQLQuery + ',DATEPART(mm,Invoice.InvoiceDate)'
         
   
   Set @SQLQuery = @SQLQuery + ' HAVING OwnersSocieties.AuthorSocietiesID = 59 
								 AND Receipt.CompaniesID = @companyId 
								 AND CopyRight.OwnersID = @idAutor '

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
         Set @SQLQuery = @SQLQuery + 'AND Works.WorkName LIKE @filtroSql '
         
    Set @SQLQuery = @SQLQuery + ') AS tmp 
		WHERE ranking BETWEEN @inicioPaginacion AND @finPaginacion order by ranking'
    
    
    /* Specify Parameter Format for all input parameters included 
     in the stmt */
    Set @ParamDefinition =  '@companyId int,
						    @idPais int,
						    @anio int,
						    @trimestre int,
							@idAutor int,
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
                @idAutor,
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




CREATE PROCEDURE sp_amountsByWorkRankingCount
    /* Input Parameters */
	@companyId int,
    @idPais int,
    @anio int,
    @trimestre int,
    @idAutor int,
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
    Set @SQLQuery = 'SELECT COUNT (DISTINCT Works.WorksID) as cantidad '
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

			  WHERE OwnersSocieties.AuthorSocietiesID = 59 
								 AND Receipt.CompaniesID = @companyId 
								 AND CopyRight.OwnersID = @idAutor '

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
         Set @SQLQuery = @SQLQuery + 'AND Works.WorkName LIKE @filtroSql '
         
    /* Specify Parameter Format for all input parameters included 
     in the stmt */
    Set @ParamDefinition =  '@companyId int,
						    @idPais int,
						    @anio int,
						    @trimestre int,
							@idAutor int,
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
                @idAutor,
                @filtroSql, 
                @inicio,
                @fin
                
    If @@ERROR <> 0 GoTo ErrorHandler
    Set NoCount OFF
    Return(0)
  
ErrorHandler:
    Return(@@ERROR)
GO




CREATE PROCEDURE sp_unitsRanking
    /* Input Parameters */
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
					 FROM (SELECT ROW_NUMBER() OVER(ORDER BY cantidadUnidades desc) AS ranking, ' 
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
    
    
    Set @SQLQuery = @SQLQuery + 'CopyRight.OwnersID AS idAutor, Owners_All.UniqueName AS nombreAutor, 0 AS montoPercibido, '
    				+ 'ROUND( SUM(RNKDetails.Units * CurrenciesConvertion.Factor * (CopyRight.CopyRightShare/100)), 0) AS cantidadUnidades '
    				+ 'FROM      CurrenciesConvertion INNER JOIN
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
              Currencies.CountriesID = Sources.CountriesID 
	                      
	GROUP BY 	  CopyRight.OwnersID, 
				  Owners_All.UniqueName, 
				  OwnersSocieties.AuthorSocietiesID, 
				  Sources.SourcesID_Parent'
    				
   If @idPais Is Not Null 
         Set @SQLQuery = @SQLQuery + ',Countries.CountriesID'
         
   If @anio Is Not Null
         Set @SQLQuery = @SQLQuery + ',DATEPART(yyyy,RNKHeaders.EndDate)'
         
   If @trimestre Is Not Null
         Set @SQLQuery = @SQLQuery + ',DATEPART(mm,RNKHeaders.EndDate)'
         
   
   Set @SQLQuery = @SQLQuery + ' HAVING OwnersSocieties.AuthorSocietiesID = 59 AND Sources.SourcesID_Parent = 9 '

   If @idPais Is Not Null 
         Set @SQLQuery = @SQLQuery + 'AND Countries.CountriesID = @idPais '
         
   If @anio Is Not Null 
         Set @SQLQuery = @SQLQuery + 'AND DATEPART(yyyy,RNKHeaders.EndDate) = @anio '
         
   If @trimestre Is Not Null 
   BEGIN
   		 Set @inicio = (@trimestre - 1) * 3 + 1
   		 Set @fin = @inicio + 2
         Set @SQLQuery = @SQLQuery + 'AND DATEPART(mm,RNKHeaders.EndDate) BETWEEN @inicio AND @fin '
   END
    
	If @filtroSql Is Not Null 
         Set @SQLQuery = @SQLQuery + 'AND Owners_All.UniqueName LIKE @filtroSql '
         
    Set @SQLQuery = @SQLQuery + ') as tmp) AS tmp2 
		WHERE ranking BETWEEN @inicioPaginacion AND @finPaginacion order by ranking'
    
    
    /* Specify Parameter Format for all input parameters included 
     in the stmt */
    Set @ParamDefinition =  '@idPais int,
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




CREATE PROCEDURE sp_unitsRankingCount
    /* Input Parameters */
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
    				+ 'FROM      CurrenciesConvertion INNER JOIN
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
              Currencies.CountriesID = Sources.CountriesID '
    				
   Set @SQLQuery = @SQLQuery + ' WHERE OwnersSocieties.AuthorSocietiesID = 59 AND Sources.SourcesID_Parent = 9 '

   If @idPais Is Not Null 
         Set @SQLQuery = @SQLQuery + 'AND Countries.CountriesID = @idPais '
         
   If @anio Is Not Null 
         Set @SQLQuery = @SQLQuery + 'AND DATEPART(yyyy,RNKHeaders.EndDate) = @anio '
         
   If @trimestre Is Not Null 
   BEGIN
   		 Set @inicio = (@trimestre - 1) * 3 + 1
   		 Set @fin = @inicio + 2
         Set @SQLQuery = @SQLQuery + 'AND DATEPART(mm,RNKHeaders.EndDate) BETWEEN @inicio AND @fin '
   END
    
	If @filtroSql Is Not Null 
         Set @SQLQuery = @SQLQuery + 'AND Owners_All.UniqueName LIKE @filtroSql '
         
    
    /* Specify Parameter Format for all input parameters included 
     in the stmt */
    Set @ParamDefinition =  '@idPais int,
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



CREATE PROCEDURE sp_unitsRankingForAuthors
    /* Input Parameters */
    @idPais int,
    @anio int,
    @trimestre int,
    @idsAutores varchar(2000)
        
AS
    Set NoCount ON
    /* Variable Declaration */
    Declare @SQLQuery AS NVarchar(4000)
    Declare @ParamDefinition AS NVarchar(2000) 
    
    Declare @inicio as int
    Declare @fin as int
    
    /* Build the Transact-SQL String with the input parameters */ 
    Set @SQLQuery = 'SELECT CopyRight.OwnersID as idAutor,
					 ROUND( SUM(RNKDetails.Units * CurrenciesConvertion.Factor * (CopyRight.CopyRightShare/100)), 0) AS cantidadUnidades '
    				+ 'FROM      CurrenciesConvertion INNER JOIN
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
              Currencies.CountriesID = Sources.CountriesID 
	                      
	GROUP BY 	  CopyRight.OwnersID, 
				  Owners_All.UniqueName, 
				  OwnersSocieties.AuthorSocietiesID, 
				  Sources.SourcesID_Parent'
    				
   If @idPais Is Not Null 
         Set @SQLQuery = @SQLQuery + ',Countries.CountriesID'
         
   If @anio Is Not Null
         Set @SQLQuery = @SQLQuery + ',DATEPART(yyyy,RNKHeaders.EndDate)'
         
   If @trimestre Is Not Null
         Set @SQLQuery = @SQLQuery + ',DATEPART(mm,RNKHeaders.EndDate)'
         
   
   Set @SQLQuery = @SQLQuery + ' HAVING OwnersSocieties.AuthorSocietiesID = 59 
								 AND Sources.SourcesID_Parent = 9 
								 AND CopyRight.OwnersID IN (' + @idsAutores + ') '

   If @idPais Is Not Null 
         Set @SQLQuery = @SQLQuery + 'AND Countries.CountriesID = @idPais '
         
   If @anio Is Not Null 
         Set @SQLQuery = @SQLQuery + 'AND DATEPART(yyyy,RNKHeaders.EndDate) = @anio '
         
   If @trimestre Is Not Null 
   BEGIN
   		 Set @inicio = (@trimestre - 1) * 3 + 1
   		 Set @fin = @inicio + 2
         Set @SQLQuery = @SQLQuery + 'AND DATEPART(mm,RNKHeaders.EndDate) BETWEEN @inicio AND @fin '
   END
    
    
    
    /* Specify Parameter Format for all input parameters included 
     in the stmt */
    Set @ParamDefinition =  '@idPais int,
						    @anio int,
						    @trimestre int,
							@inicio int,
							@fin int'
    /* Execute the Transact-SQL String with all parameter value's 
       Using sp_executesql Command */
    Execute sp_Executesql     
    			@SQLQuery, 
                @ParamDefinition, 
                @idPais,
                @anio, 
                @trimestre, 
                @inicio,
                @fin
                
    If @@ERROR <> 0 GoTo ErrorHandler
    Set NoCount OFF
    Return(0)
  
ErrorHandler:
    Return(@@ERROR)
GO



CREATE PROCEDURE sp_unitsByWork
    /* Input Parameters */
    @idPais int,
    @anio int,
    @trimestre int,
    @idAutor int,
    @idsCanciones varchar(5000)
        
AS
    Set NoCount ON
    /* Variable Declaration */
    Declare @SQLQuery AS NVarchar(4000)
    Declare @ParamDefinition AS NVarchar(2000) 
    
    Declare @inicio as int
    Declare @fin as int
    
    /* Build the Transact-SQL String with the input parameters */ 
    Set @SQLQuery = 'SELECT Works.WorksID as idCancion,
					 ROUND( SUM(RNKDetails.Units * CurrenciesConvertion.Factor * (CopyRight.CopyRightShare/100)), 0) AS cantidadUnidades '
    				+ 'FROM      CurrenciesConvertion INNER JOIN
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
              Currencies.CountriesID = Sources.CountriesID 
	                      
	GROUP BY 	  Works.WorksID,
				  CopyRight.OwnersID, 
				  OwnersSocieties.AuthorSocietiesID, 
				  Sources.SourcesID_Parent'
    				
   If @idPais Is Not Null 
         Set @SQLQuery = @SQLQuery + ',Countries.CountriesID'
         
   If @anio Is Not Null
         Set @SQLQuery = @SQLQuery + ',DATEPART(yyyy,RNKHeaders.EndDate)'
         
   If @trimestre Is Not Null
         Set @SQLQuery = @SQLQuery + ',DATEPART(mm,RNKHeaders.EndDate)'
         
   
   Set @SQLQuery = @SQLQuery + ' HAVING OwnersSocieties.AuthorSocietiesID = 59 
								 AND Sources.SourcesID_Parent = 9 
								 AND CopyRight.OwnersID = @idAutor
								 AND Works.WorksID IN (' + @idsCanciones + ') '

   If @idPais Is Not Null 
         Set @SQLQuery = @SQLQuery + 'AND Countries.CountriesID = @idPais '
         
   If @anio Is Not Null 
         Set @SQLQuery = @SQLQuery + 'AND DATEPART(yyyy,RNKHeaders.EndDate) = @anio '
         
   If @trimestre Is Not Null 
   BEGIN
   		 Set @inicio = (@trimestre - 1) * 3 + 1
   		 Set @fin = @inicio + 2
         Set @SQLQuery = @SQLQuery + 'AND DATEPART(mm,RNKHeaders.EndDate) BETWEEN @inicio AND @fin '
   END
    
    
    
    /* Specify Parameter Format for all input parameters included 
     in the stmt */
    Set @ParamDefinition =  '@idPais int,
						    @anio int,
						    @trimestre int,
							@idAutor int,
							@inicio int,
							@fin int'
    /* Execute the Transact-SQL String with all parameter value's 
       Using sp_executesql Command */
    Execute sp_Executesql     
    			@SQLQuery, 
                @ParamDefinition, 
                @idPais,
                @anio, 
                @trimestre, 
                @idAutor,
                @inicio,
                @fin
                
    If @@ERROR <> 0 GoTo ErrorHandler
    Set NoCount OFF
    Return(0)
  
ErrorHandler:
    Return(@@ERROR)
GO



CREATE PROCEDURE sp_MoneyAmounts
    @inicioPaginacion int,
    @finPaginacion int
        
AS
    Set NoCount ON
    
    SELECT * FROM (

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
		            Rights.RightName AS nombreDerechoExterno, 
		            Rights.RightsID AS idDerechoExterno,
		            Rights.RightsID_Parent AS idDerechoPadre,
		            Receipt.CurrencyFactor * SUM(CollectionDetails.AmmountReceived) * (CopyRight.CopyRightShare/100) AS montoPercibido,
		            0 AS cantidadUnidades
		
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
		              Rights.RightsID,
		              Rights.RightsID_Parent,
		              Receipt.CurrencyFactor, 
		              Invoice.InvoiceDate, 
		              Receipt.CompaniesID
		                      
		HAVING      (OwnersSocieties.AuthorSocietiesID = 59)
		
	) AS tmp WHERE id BETWEEN @inicioPaginacion AND @finPaginacion
	
    If @@ERROR <> 0 GoTo ErrorHandler
    Set NoCount OFF
    Return(0)
  
ErrorHandler:
    Return(@@ERROR)
GO




CREATE PROCEDURE sp_Units
    @inicioPaginacion int,
    @finPaginacion int
        
AS
    Set NoCount ON
    
    SELECT * FROM (

	    SELECT     	ROW_NUMBER() OVER(ORDER BY Works.WorksID) AS id,
	    			null AS companyId,
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
		            null AS idFuente,
		            null AS nombreFuente,
		            Rights.RightName AS nombreDerechoExterno,
		            Rights.RightsID AS idDerechoExterno,
		            Rights.RightsID_Parent AS idDerechoPadre,
		            0 AS montoPercibido,
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
		              Rights.RightsID,
		              Rights.RightsID_Parent,
		              Sources.SourcesID_Parent,
		              RNKHeaders.EndDate,
		              CurrenciesConvertion.Factor
	                      
		HAVING      (OwnersSocieties.AuthorSocietiesID = 59) AND (Sources.SourcesID_Parent = 9)
		
	) AS tmp WHERE id BETWEEN @inicioPaginacion AND @finPaginacion
	
    If @@ERROR <> 0 GoTo ErrorHandler
    Set NoCount OFF
    Return(0)
  
ErrorHandler:
    Return(@@ERROR)
GO