"use client";

import { useState } from "react";
import { Document, Page, pdfjs } from "react-pdf";

import "react-pdf/dist/esm/Page/AnnotationLayer.css";
import "react-pdf/dist/esm/Page/TextLayer.css";
import type { PDFDocumentProxy } from "pdfjs-dist";

import { Tabs, TabsContent, TabsList, TabsTrigger } from "../ui/tabs";
import PDFUploadForm from "./pdf-upload-form";

pdfjs.GlobalWorkerOptions.workerSrc = new URL(
  "pdfjs-dist/build/pdf.worker.min.js",
  import.meta.url
).toString();

const options = {
  cMapUrl: "/cmaps/",
  standardFontDataUrl: "/standard_fonts/",
};

type PDFFile = string | File | null;

export default function PDFSection() {
  const [pdfFiles, setPdfFiles] = useState<PDFFile[] | null>(null);
  const [fileNames, setFileNames] = useState<string[]>([]);

  console.log(pdfFiles);
  const [numPages, setNumPages] = useState<number>();

  function onFileChange(event: React.ChangeEvent<HTMLInputElement>): void {
    const { files } = event.target;

    if (files) {
      setPdfFiles(Array.from(files));
      setFileNames(Array.from(files).map((file) => file.name));
    }
  }

  function onDocumentLoadSuccess({
    numPages: nextNumPages,
  }: PDFDocumentProxy): void {
    setNumPages(nextNumPages);
  }

  return (
    <div className="flex h-screen w-full items-start overflow-y-auto">
      {pdfFiles === null ? (
        <div className="flex h-full w-full items-center justify-center gap-5">
          <label htmlFor="file">Load files:</label>
          <PDFUploadForm parentOnChange={onFileChange} />
        </div>
      ) : (
        <Tabs defaultValue={"0"}>
          <TabsList>
            {fileNames.map((fileName, index) => (
              <TabsTrigger key={index} value={index.toString()}>
                {fileName}
              </TabsTrigger>
            ))}
          </TabsList>
          <div className="flex h-full w-full justify-center">
            {pdfFiles.map((pdfFile, index) => (
              <TabsContent key={index} value={index.toString()}>
                <Document
                  file={pdfFile}
                  onLoadSuccess={onDocumentLoadSuccess}
                  options={options}
                >
                  {Array.from(new Array(numPages), (el, index) => (
                    <Page key={`page_${index + 1}`} pageNumber={index + 1} />
                  ))}
                </Document>
              </TabsContent>
            ))}
          </div>
        </Tabs>
      )}
    </div>
  );
}
