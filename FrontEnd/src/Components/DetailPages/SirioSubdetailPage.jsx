import React, { Component } from 'react';
import { Table } from 'react-bootstrap';
import classes from './SirioDetailPage.module.css';

class SirioSubdetailPage extends Component {

    render() {
        return (
            <div>
                <div className={classes.headerWrapper}>
                    <h2 className={classes.title}>
                        {this.props.title}
                    </h2>
                </div>
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
                </div>
            </div>
        );
    }

};
export default SirioSubdetailPage;
