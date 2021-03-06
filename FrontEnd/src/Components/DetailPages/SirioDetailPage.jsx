import React, { Component } from 'react';
import { Table } from 'react-bootstrap';
import classes from './SirioDetailPage.module.css';

class SirioDetailPage extends Component {

    render() {
        const { noBack, title, link, data, subButton } = this.props;
        const { headerWrapper, logoImage, rowItem, toolkitWrapper } = classes;

        var backer;
        if (link) {
            backer =
                <a href={'/' + link}>
                    <img src={process.env.PUBLIC_URL + '/backLogo.png'} className={logoImage} alt="Back Logo" data-tip="Kembali" />
                </a>
                ;
        } else {
            backer =
                <div onClick={() => window.history.back()}>
                    <img src={process.env.PUBLIC_URL + '/backLogo.png'} className={logoImage} alt="Back Logo" data-tip="Kembali" />
                </div>
                ;
        }

        return (
            <div>
                <div className={headerWrapper}>
                    {!noBack && backer}
                    <h2 className={classes.title}>
                        {title}
                    </h2>
                </div>
                <div className={toolkitWrapper}>
                    <Table responsive>
                        <tbody>
                            {Object.keys(data).map((key, i) =>
                                <tr key={i}>
                                    <td className={rowItem} key={1}>{key}</td>
                                    <td className={rowItem} key={2}>
                                        <div className="w-100" style={{ whiteSpace: 'pre-line' }}>
                                            {data[key]}
                                        </div>
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </Table>
                    <div className="w-100 text-right">
                        {subButton}
                    </div>
                </div>
            </div>
        );
    }
};

export default SirioDetailPage;
