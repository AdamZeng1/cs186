#! /usr/bin/env python

"""Parses Project Gutenberg ebooks files. Prints ebooks csv to standard error and tokens csv to standard out."""

import sys, re

class Parser:
    def __init__(self):
        self._inbody = False
        self._crlf = '\r\n'
        self._delimeter = re.compile('\W|-|_')
        self._tokens = ('Title', 'Author', 'Release Date', 'EBook #', 'Language')
        self._start = '*** START OF THE PROJECT GUTENBERG'
        self._end = '*** END OF THE PROJECT GUTENBERG'
        self._header =  'title,author,release_date,ebook_id,language,body'
        self._values = ['null' for token in xrange(5)]

    def parse(self, file):
        with open(file, 'rb') as ebook:
            for line in ebook:
                if self._inbody:
                    self._writeBodyLine(line)
                    continue
                if line.startswith(self._start):
                    self._writeHeader()
                    continue
                self._parseLine(line)

    def _parseLine(self, line):
        chunks = line.split(':')
        if chunks[0] in self._tokens:
            index = self._tokens.index(chunks[0])
            self._values[index] = chunks[1][1:-2]

    def _writeHeader(self):
        sys.stderr.write(self._composeHeader())
        self._inbody = True

    def _writeBodyLine(self, line):
        if line.startswith(self._end):
            self._inbody = False
            sys.stderr.write('"' + self._crlf)
            return
        sys.stderr.write(line)
        self._writeWords(line)

    def _composeHeader(self):
        self._extractId()
        header = [self._header, self._crlf]
        for value in self._values:
            header.append(value)
            header.append(',')
        header.append('"')
        return ''.join(header)

    def _extractId(self):
        chunks = self._values[2].split(self._tokens[3])
        self._values[2] = '"' + chunks[0][:-2] + '"'
        self._values[3] = chunks[1][:-1]

    def _writeWords(self, line):
        if not line.isspace():
            for word in self._delimeter.split(line.strip()):
                if word.isalpha():
                    sys.stdout.write(self._values[3] + ',' + word.lower() + self._crlf)

def main():
    parser = Parser()
    filename = sys.argv[1]
    parser.parse(filename)

if __name__ == '__main__':
    main()

