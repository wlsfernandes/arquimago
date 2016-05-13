' See http://msdn2.microsoft.com/en-us/library/bb238158.aspx
Const wdFormatPDF = 17  ' PDF format. 
Const wdFormatXPS = 18  ' XPS format. 
 
Const WdDoNotSaveChanges = 0
 
Dim arguments
Set arguments = WScript.Arguments
 
' Make sure that there are one or two arguments
Function CheckUserArguments()
  If arguments.Unnamed.Count < 1 Or arguments.Unnamed.Count > 2 Then
    WScript.Echo "Use:"
    WScript.Echo "<script> input.doc"
    WScript.Echo "<script> input.doc output.pdf"
    WScript.Quit 1
  End If
End Function
 
 
' Transforms a doc to a pdf
Function DocToPdf( docInputFile, pdfOutputFile )
 
  Dim fileSystemObject
  Dim wordApplication
  Dim wordDocument
  Dim wordDocuments
  Dim baseFolder
 
  Set fileSystemObject = CreateObject("Scripting.FileSystemObject")
  Set wordApplication = CreateObject("Word.Application")
  Set wordDocuments = wordApplication.Documents
 
  docInputFile = fileSystemObject.GetAbsolutePathName(docInputFile)
  baseFolder = fileSystemObject.GetParentFolderName(docInputFile)
 
  If Len(pdfOutputFile) = 0 Then
    pdfOutputFile = fileSystemObject.GetBaseName(docInputFile) + ".pdf"
  End If
 
  If Len(fileSystemObject.GetParentFolderName(pdfOutputFile)) = 0 Then
    pdfOutputFile = baseFolder + "\" + pdfOutputFile
  End If
 
  ' Disable any potential macros of the word document.
  wordApplication.WordBasic.DisableAutoMacros
 
  Set wordDocument = wordDocuments.Open(docInputFile)
 
  ' See http://msdn2.microsoft.com/en-us/library/bb221597.aspx 
  wordDocument.SaveAs pdfOutputFile, wdFormatPDF
 
  wordDocument.Close WdDoNotSaveChanges
  wordApplication.Quit WdDoNotSaveChanges
   
  Set wordApplication = Nothing
  Set fileSystemObject = Nothing
 
End Function
 
' Execute script
Call CheckUserArguments()
If arguments.Unnamed.Count = 2 Then
 Call DocToPdf( arguments.Unnamed.Item(0), arguments.Unnamed.Item(1) )
Else
 Call DocToPdf( arguments.Unnamed.Item(0), "" )
End If
 
Set arguments = Nothing