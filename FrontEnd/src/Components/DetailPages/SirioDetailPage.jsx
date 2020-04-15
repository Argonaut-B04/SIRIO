import React, { Component } from 'react';
import { Table } from 'react-bootstrap';
import classes from './SirioDetailPage.module.css';

class SirioDetailPage extends Component {

    render() {
        const { noBack, link, title, data, subButton } = this.props;
        const { headerWrapper, logoImage, rowItem, toolkitWrapper } = classes;
        return (
            <div>
                <div className={headerWrapper}>
                    {!noBack &&
                        <a href={'/' + link}>
                            <img src={process.env.PUBLIC_URL + '/backLogo.png'} className={logoImage} alt="Back Logo" />
                        </a>
                    }
                    <h2 className={classes.title}>
                        {title}
                    </h2>
                </div>
                <div className={toolkitWrapper}>
                    <Table responsive>
                        <tbody>
                            {Object.keys(data).map((key, i) =>
                                <tr key={i}>
                                    <td className={rowItem}>{key}</td>
                                    <td className={rowItem}>{data[key]}</td>
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
