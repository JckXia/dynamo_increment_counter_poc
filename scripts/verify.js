const fs = require('fs')
const csv = require('fast-csv');
 
const hashMap = {};

handleRows = (row) => {
    if(hashMap[row.student_id]) {
        console.error(`Error! ${row.student_id} was already defined `);
        throw Error(`Error! ${row.student_id} was already defined`);
    }
    hashMap[row.student_id] = row;
}  

fs.createReadStream('./dynamoResults.csv')
.pipe(csv.parse({ headers: true }))
.on('error', error => console.error(error))
.on('data', row => handleRows(row))
.on('end', () => { console.log(`Received ${Object.keys(hashMap).length} student objects, all ids are unique!`) });

 