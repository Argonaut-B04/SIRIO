import React, { Component } from 'react';
import { Table } from 'react-bootstrap';
import classes from './SirioDetailPage.module.css';

class SirioDetailPage extends Component {

    render() {
        return (
            <div>
                <div className={classes.headerWrapper}>
                    {!this.props.noBack &&
                        <a href={'/' + this.props.link}>
                            <img src={process.env.PUBLIC_URL + '/backLogo.png'} className={classes.logoImage} alt="Back Logo" />
                        </a>
                    }
                    <h2 className={classes.title}>
                        {this.props.title}
                    </h2>
                </div>
                {this.props.title &&
                    <div className={classes.headerWrapper}>
                        <h2 className={classes.title}>
                            {this.props.title}
                        </h2>
                    </div>
                }
                <div className={classes.toolkitWrapper}>
                    <Table responsive>
                        <tbody>
                            {Object.keys(this.props.data).map((key, i) =>
                                <tr key={i}>
                                    <td className={classes.rowItem}>{key}</td>
                                    <td className={classes.rowItem}>{this.props.data[key]}</td>
                                </tr>
                            )}
                        </tbody>
                    </Table>
                    <div className="w-100 text-right">
                        {this.props.subButton}
                    </div>
                </div>
            </div>
        );
    }

};
export default SirioDetailPage;
