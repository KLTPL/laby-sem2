#ifndef PASCALTRIANGLEROW_H
#define PASCALTRIANGLEROW_H

class PascalTriangleRow {
public:
    long long n;
    long long* row;
    PascalTriangleRow(long long n);
    ~PascalTriangleRow();     
    void fill();            
};

#endif