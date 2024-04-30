const express = require('express');
const multer = require('multer');
const upload = multer({dest: './static/uploads/'});
const hbs = require('hbs');

const app = express();
const PORT = process.env.PORT || 80;
//const PORT = 3000;

const html_path = __dirname + '/templates/';

//Configure handlebars tempalte engine
app.set('view engine', 'hbs');
app.set('views', 'templates');

// Set up Middleware
app.use(express.static('static'));
app.use(express.urlencoded({extended: true}));

// Routes
app.get('/', (req, res) => {
    res.sendFile(html_path + 'form.html');
});
  
app.post('/send', upload.single('imgInput'), (req, res) => {
    console.log(req.file);
    console.log(req.body);

    if (req.body.sFirst.toLowerCase() == "stu" || req.body.sFirst.toLowerCase() == "stuart") {
        if (req.body.sLast.toLowerCase() == "dent") {
            res.sendFile(html_path + 'error.html');
            return;
        }
    }

    if (req.body.rFirst.toLowerCase() == "stu" || req.body.rFirst.toLowerCase() == "stuart") {
        if (req.body.rLast.toLowerCase() == "dent") {
            res.sendFile(html_path + 'error.html');
            return;
        }
    }
  
    try {
        cardNum = "**** ";
        for (let i = req.body.cNumber.length - 4; i < req.body.cNumber.length; i++) {
            cardNum += req.body.cNumber[i];
        }

        let data = {
            image: req.file.filename,
            sFirst: req.body.sFirst,
            sLast: req.body.sLast,
            rFirst: req.body.rFirst,
            rLast: req.body.rLast,
            message: req.body.message,
            email: req.body.email,
            phone: req.body.phone,
            card: req.body.card,
            cNumber: cardNum,
            date: new Date(),
            amount: req.body.amount,
        }
        res.render('success', data);
        //res.send(req.body);
    }
    catch(err) {
        res.sendFile(html_path + 'error.html');
    }

    console.log("Still Here");
});
  
// As our server to listen for incoming connections
app.listen(PORT, () => console.log(`Server listening on port: ${PORT}`));